package com.mystartup.siretmicroservice.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "business", types = BusinessDto.class)
public interface BusinessProjection {
  String getId();

  String getNic();

  String getCreationDate();

  String getAddress();

  @Value("#{target.company.fullName}")
  String getFullName();

  @Value("#{target.company.tvaNumber}")
  String getTvaNumber();
}