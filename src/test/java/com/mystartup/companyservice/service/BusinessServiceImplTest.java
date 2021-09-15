package com.mystartup.companyservice.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.mystartup.companyservice.model.BusinessDto;
import com.mystartup.companyservice.model.Company;
import com.mystartup.companyservice.model.Siret;
import com.mystartup.companyservice.repositories.BusinessRepository;
import com.mystartup.companyservice.repositories.SiretRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BusinessServiceImplTest {
  @Mock
  SiretRepository siretRepository;
  @Mock
  RestTemplate restTemplate;
  @Mock
  BusinessRepository businessRepository;

  ListAppender<ILoggingEvent> loggingAppender = new ListAppender<>();

  @InjectMocks
  private BusinessService businessService;

  @BeforeAll
  public void setUp() {
    businessService = new BusinessServiceImpl(siretRepository, restTemplate, businessRepository,
                                              "http://www.fakeSireneApi.com");
    Logger logger = (Logger)LoggerFactory.getLogger(BusinessServiceImpl.class);
    // create and start a ListAppender
    loggingAppender.start();
    // add the appender to the logger
    logger.addAppender(loggingAppender);
  }

  @AfterEach
  public void clearLogger() {
    loggingAppender.list.clear();
  }

  @DisplayName("Test update when no Siret numbers returned from the DB")
  @Test
  void noSiretNumbersStored() {
    when(siretRepository.findAll()).thenReturn(null);
    assertEquals(Collections.emptyList(), businessService.updateBusinesses());

    when(siretRepository.findAll()).thenReturn(Collections.emptyList());
    assertEquals(Collections.emptyList(), businessService.updateBusinesses());
  }

  @DisplayName("Test update when no Siret in returned by Sirene Api")
  @Test
  void noSiretNumbersSirene() {

    when(siretRepository.findAll()).thenReturn(Collections.singletonList(new Siret("12345")));
    when(restTemplate.getForObject("http://www.fakeSireneApi.com/etablissements/12345", BusinessDto.class))
        .thenReturn(null);
    assertEquals(Collections.emptyList(), businessService.updateBusinesses());

  }

  @DisplayName("Test nominal update")
  @Test
  void nominalUpdate() {
    BusinessDto businessDto = new BusinessDto();
    businessDto.setId("1");
    businessDto.setSiret("12345");
    businessDto.setNic("nic");
    businessDto.setAddress("address");
    businessDto.setCompany(new Company("id1", "Company Name", "tva number", null));
    businessDto.setCreationDate("2020-02-20");

    when(siretRepository.findAll()).thenReturn(Collections.singletonList(new Siret("12345")));
    when(restTemplate.getForObject("http://www.fakeSireneApi.com/etablissements/12345", BusinessDto.class))
        .thenReturn(businessDto);
    when(businessRepository.saveAll(Collections.singletonList(businessDto)))
        .thenReturn(Collections.singletonList(businessDto));
    Iterable<BusinessDto> actualBusiness = businessService.updateBusinesses();
    List<BusinessDto> businessesList = StreamSupport.stream(actualBusiness.spliterator(), false)
        .collect(Collectors.toList());
    assertEquals(1, businessesList.size());
    assertEquals("Company Name", businessesList.get(0).getCompany().getFullName());
    assertEquals("tva number", businessesList.get(0).getCompany().getTvaNumber());
    assertEquals("address", businessesList.get(0).getAddress());
    assertEquals("nic", businessesList.get(0).getNic());
    assertEquals("12345", businessesList.get(0).getSiret());
    assertEquals("2020-02-20", businessesList.get(0).getCreationDate());
    assertEquals(Collections.singletonList(businessDto), actualBusiness);
    verify(businessRepository, times(1)).saveAll(Collections.singletonList(businessDto));

  }

  @DisplayName("Test update with Sirene API throwing 429 - Too many requests")
  @Test
  void testUpdate429Response() {
    when(siretRepository.findAll()).thenReturn(Collections.singletonList(new Siret("12345")));
    when(restTemplate.getForObject("http://www.fakeSireneApi.com/etablissements/12345", BusinessDto.class))
        .thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));
    assertEquals(Collections.emptyList(), businessService.updateBusinesses());

    List<ILoggingEvent> logsList = loggingAppender.list;
    assertEquals("Error while retrieving SIRET number 12345 because 429 TOO_MANY_REQUESTS", logsList.get(0)
        .getMessage());
    assertEquals(Level.ERROR, logsList.get(0).getLevel());
  }

  @DisplayName("Test update with Sirene API Not Available")
  @Test
  void updateUnavailableResponse() {

    when(siretRepository.findAll()).thenReturn(Collections.singletonList(new Siret("12345")));
    when(restTemplate.getForObject("http://www.fakeSireneApi.com/etablissements/12345", BusinessDto.class))
        .thenThrow(new RestClientException("Runtime exception"));
    assertEquals(Collections.emptyList(), businessService.updateBusinesses());

    List<ILoggingEvent> logsList = loggingAppender.list;
    assertEquals("Error while querying the Sirene API for SIRET number 12345 : Runtime exception", logsList.get(0)
        .getMessage());
    assertEquals(Level.ERROR, logsList.get(0).getLevel());
  }

}