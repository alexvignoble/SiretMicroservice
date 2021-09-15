package com.mystartup.companyservice.service;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mystartup.companyservice.model.BusinessDto;
import com.mystartup.companyservice.model.Siret;
import com.mystartup.companyservice.repositories.BusinessRepository;
import com.mystartup.companyservice.repositories.SiretRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class BusinessServiceImpl implements BusinessService {

  SiretRepository siretRepository;

  RestTemplate restTemplate;

  BusinessRepository businessRepository;

  private final String sireneApi;

  public BusinessServiceImpl(final SiretRepository siretRepository, final RestTemplate restTemplate,
                             final BusinessRepository businessRepository, @Value("${sirene.api}")
                                 String sireneApi) {
    this.siretRepository = siretRepository;
    this.restTemplate = restTemplate;
    this.businessRepository = businessRepository;
    this.sireneApi = sireneApi;
  }

  @Async
  @Override
  public Iterable<BusinessDto> updateBusinesses() {

    List<Siret> configuredSirets = siretRepository.findAll();

    if (configuredSirets == null || configuredSirets.isEmpty()) {
      return Collections.emptyList();
    }

    List<BusinessDto> businesses = StreamSupport.stream(configuredSirets.spliterator(), false)
        .map(this::retrieveBusiness)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toList());

    return businessRepository.saveAll(businesses);
  }

  private Optional<BusinessDto> retrieveBusiness(final Siret siret) {
    URI builder = UriComponentsBuilder.fromHttpUrl(sireneApi)
        .path("/etablissements/{siretCode}")
        .build(siret.getCode());
    try {
      return Optional.ofNullable(restTemplate.getForObject(builder.toString(), BusinessDto.class));
    } catch (HttpStatusCodeException ex) {
      log.error(String.format("Error while retrieving SIRET number %s because %s", siret.getCode(), ex.getMessage()));
    } catch (RestClientException ex) {
      log.error(String.format("Error while querying the Sirene API for SIRET number %s : %s", siret.getCode(),
                              ex.getLocalizedMessage()));
    }
    return Optional.empty();
  }
}
