package com.mystartup.siretmicroservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.mystartup.siretmicroservice.model.Siret;
import com.mystartup.siretmicroservice.repositories.SiretRepository;
import com.mystartup.siretmicroservice.service.BusinessService;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAsync
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Value("${default.sirets}")
  private List<String> defaultSirets;

  @Autowired
  BusinessService businessService;

  @Bean
  CommandLineRunner initialize(SiretRepository siretRepository) {
    return args -> {
      defaultSirets.forEach(code -> siretRepository.save(new Siret(code)));
      log.info(String.format("Initialized DB with default siret numbers: %s", defaultSirets));
      businessService.updateBusinesses();
      log.info("Updated DB with business information");
    };
  }
}
