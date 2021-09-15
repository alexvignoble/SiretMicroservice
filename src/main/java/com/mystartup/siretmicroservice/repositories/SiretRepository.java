package com.mystartup.siretmicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mystartup.siretmicroservice.model.Siret;

@RepositoryRestResource(collectionResourceRel = "sirets", path = "sirets")
public interface SiretRepository extends JpaRepository<Siret, Long> {
}
