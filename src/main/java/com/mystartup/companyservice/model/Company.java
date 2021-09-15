package com.mystartup.companyservice.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="company")
public class Company {
  @Id
  private String id;
  @JsonProperty("denomination")
  private String fullName;
  @JsonProperty("numero_tva_intra")
  private String tvaNumber;
  @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
  private List<BusinessDto> businesses;
}
