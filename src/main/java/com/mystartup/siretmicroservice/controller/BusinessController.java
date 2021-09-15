package com.mystartup.siretmicroservice.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mystartup.siretmicroservice.service.BusinessService;

@RestController
public class BusinessController {

  BusinessService businessService;

  public BusinessController(final BusinessService businessService) {
    this.businessService = businessService;
  }

  @GetMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody Map<String, String> update() {
    businessService.updateBusinesses();
    return Collections.singletonMap("status", "Successfully triggered the async businesses update");
  }
}