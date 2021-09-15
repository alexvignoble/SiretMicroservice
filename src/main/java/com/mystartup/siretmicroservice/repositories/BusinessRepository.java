package com.mystartup.siretmicroservice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mystartup.siretmicroservice.model.BusinessDto;
import com.mystartup.siretmicroservice.model.BusinessProjection;

@RepositoryRestResource(excerptProjection = BusinessProjection.class, itemResourceRel = "business", collectionResourceRel = "businesses", path = "businesses")
public interface BusinessRepository extends PagingAndSortingRepository<BusinessDto, String> {
}
