package com.mystartup.companyservice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mystartup.companyservice.model.BusinessDto;
import com.mystartup.companyservice.model.BusinessProjection;

@RepositoryRestResource(excerptProjection = BusinessProjection.class, itemResourceRel = "business", collectionResourceRel = "businesses", path = "businesses")
public interface BusinessRepository extends PagingAndSortingRepository<BusinessDto, String> {
}
