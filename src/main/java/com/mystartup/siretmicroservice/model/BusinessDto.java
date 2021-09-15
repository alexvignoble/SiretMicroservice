package com.mystartup.siretmicroservice.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@JsonRootName(value = "etablissement")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "business")
public class BusinessDto implements Serializable {
  @JsonProperty("id")
  @Id
  private String id;
  @JsonProperty("nic")
  private String nic;
  @JsonProperty("siret")
  private String siret;
  @JsonProperty("date_creation")
  private String creationDate;
  @JsonProperty("geo_adresse")
  private String address;
  @JsonProperty("unite_legale")
  @ManyToOne(cascade = CascadeType.ALL)
  private Company company;
}
