package com.mystartup.siretmicroservice.service;

import com.mystartup.siretmicroservice.model.BusinessDto;

public interface BusinessService {

  Iterable<BusinessDto> updateBusinesses();
}
