package com.mystartup.companyservice.service;

import com.mystartup.companyservice.model.BusinessDto;

public interface BusinessService {

  Iterable<BusinessDto> updateBusinesses();
}
