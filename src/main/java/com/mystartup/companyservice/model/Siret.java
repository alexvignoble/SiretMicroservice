package com.mystartup.companyservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sirets")
@Data
@NoArgsConstructor
public class Siret {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  private String code;

  public Siret(final String code) {
    this.code = code;
  }
}

