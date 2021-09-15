package com.mystartup.siretmicroservice;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(
    locations = "classpath:application-integration.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ApplicationIntegrationTests {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private MockMvc mvc;

  private MockRestServiceServer mockServer;

  @BeforeAll
  public void init() throws URISyntaxException {
    mockServer = MockRestServiceServer.createServer(restTemplate);

    mockServer.expect(ExpectedCount.once(),
                      requestTo(new URI("https://localhost/api/sirene/v3/etablissements/31302979500017")))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).body(
                "{\"etablissement\":{\"id\":1522897774,\"siren\":\"313029795\",\"nic\":\"00017\",\"siret\":\"31302979500017\",\"statut_diffusion\":\"O\",\"date_creation\":\"1978-01-01\",\"tranche_effectifs\":\"12\",\"annee_effectifs\":\"2018\",\"activite_principale_registre_metiers\":null,\"date_dernier_traitement\":\"2020-08-25T10:25:36\",\"etablissement_siege\":\"true\",\"nombre_periodes\":\"4\",\"complement_adresse\":null,\"numero_voie\":\"261\",\"indice_repetition\":null,\"type_voie\":\"CHE\",\"libelle_voie\":\"DES COLLES\",\"code_postal\":\"06140\",\"libelle_commune\":\"VENCE\",\"libelle_commune_etranger\":null,\"distribution_speciale\":null,\"code_commune\":\"06157\",\"code_cedex\":null,\"libelle_cedex\":null,\"code_pays_etranger\":null,\"libelle_pays_etranger\":null,\"complement_adresse_2\":null,\"numero_voie_2\":null,\"indice_repetition_2\":null,\"type_voie_2\":null,\"libelle_voie_2\":null,\"code_postal_2\":null,\"libelle_commune_2\":null,\"libelle_commune_etranger_2\":null,\"distribution_speciale_2\":null,\"code_commune_2\":null,\"code_cedex_2\":null,\"libelle_cedex_2\":null,\"code_pays_etranger_2\":null,\"libelle_pays_etranger_2\":null,\"date_debut\":\"2012-12-31\",\"etat_administratif\":\"A\",\"enseigne_1\":null,\"enseigne_2\":null,\"enseigne_3\":null,\"denomination_usuelle\":null,\"activite_principale\":\"81.30Z\",\"nomenclature_activite_principale\":\"NAFRev2\",\"caractere_employeur\":\"O\",\"longitude\":\"7.08771\",\"latitude\":\"43.722334\",\"geo_score\":\"0.85\",\"geo_type\":\"housenumber\",\"geo_adresse\":\"261 Chemin des Colles 06140 Vence\",\"geo_id\":\"06157_0310_00261\",\"geo_ligne\":\"G\",\"geo_l4\":\"261 CHEMIN DES COLLES\",\"geo_l5\":null,\"unite_legale_id\":227104352,\"created_at\":\"2021-08-03T02:10:04.712+02:00\",\"updated_at\":\"2021-08-03T02:10:04.712+02:00\",\"unite_legale\":{\"id\":227104352,\"siren\":\"313029795\",\"statut_diffusion\":\"O\",\"unite_purgee\":null,\"date_creation\":\"1978-01-01\",\"sigle\":null,\"sexe\":null,\"prenom_1\":null,\"prenom_2\":null,\"prenom_3\":null,\"prenom_4\":null,\"prenom_usuel\":null,\"pseudonyme\":null,\"identifiant_association\":null,\"tranche_effectifs\":\"12\",\"annee_effectifs\":\"2018\",\"date_dernier_traitement\":\"2020-08-25T10:25:36\",\"nombre_periodes\":\"4\",\"categorie_entreprise\":\"PME\",\"annee_categorie_entreprise\":\"2018\",\"date_fin\":null,\"date_debut\":\"2012-12-31\",\"etat_administratif\":\"A\",\"nom\":null,\"nom_usage\":null,\"denomination\":\"SOC EXPL PEPINIERES GAUDISSART\",\"denomination_usuelle_1\":null,\"denomination_usuelle_2\":null,\"denomination_usuelle_3\":null,\"categorie_juridique\":\"5499\",\"activite_principale\":\"81.30Z\",\"nomenclature_activite_principale\":\"NAFRev2\",\"nic_siege\":\"00017\",\"economie_sociale_solidaire\":null,\"caractere_employeur\":\"O\",\"created_at\":\"2021-08-03T02:05:15.598+02:00\",\"updated_at\":\"2021-08-03T02:05:15.598+02:00\",\"etablissement_siege\":{\"id\":1522897774,\"siren\":\"313029795\",\"nic\":\"00017\",\"siret\":\"31302979500017\",\"statut_diffusion\":\"O\",\"date_creation\":\"1978-01-01\",\"tranche_effectifs\":\"12\",\"annee_effectifs\":\"2018\",\"activite_principale_registre_metiers\":null,\"date_dernier_traitement\":\"2020-08-25T10:25:36\",\"etablissement_siege\":\"true\",\"nombre_periodes\":\"4\",\"complement_adresse\":null,\"numero_voie\":\"261\",\"indice_repetition\":null,\"type_voie\":\"CHE\",\"libelle_voie\":\"DES COLLES\",\"code_postal\":\"06140\",\"libelle_commune\":\"VENCE\",\"libelle_commune_etranger\":null,\"distribution_speciale\":null,\"code_commune\":\"06157\",\"code_cedex\":null,\"libelle_cedex\":null,\"code_pays_etranger\":null,\"libelle_pays_etranger\":null,\"complement_adresse_2\":null,\"numero_voie_2\":null,\"indice_repetition_2\":null,\"type_voie_2\":null,\"libelle_voie_2\":null,\"code_postal_2\":null,\"libelle_commune_2\":null,\"libelle_commune_etranger_2\":null,\"distribution_speciale_2\":null,\"code_commune_2\":null,\"code_cedex_2\":null,\"libelle_cedex_2\":null,\"code_pays_etranger_2\":null,\"libelle_pays_etranger_2\":null,\"date_debut\":\"2012-12-31\",\"etat_administratif\":\"A\",\"enseigne_1\":null,\"enseigne_2\":null,\"enseigne_3\":null,\"denomination_usuelle\":null,\"activite_principale\":\"81.30Z\",\"nomenclature_activite_principale\":\"NAFRev2\",\"caractere_employeur\":\"O\",\"longitude\":\"7.08771\",\"latitude\":\"43.722334\",\"geo_score\":\"0.85\",\"geo_type\":\"housenumber\",\"geo_adresse\":\"261 Chemin des Colles 06140 Vence\",\"geo_id\":\"06157_0310_00261\",\"geo_ligne\":\"G\",\"geo_l4\":\"261 CHEMIN DES COLLES\",\"geo_l5\":null,\"unite_legale_id\":227104352,\"created_at\":\"2021-08-03T02:10:04.712+02:00\",\"updated_at\":\"2021-08-03T02:10:04.712+02:00\"},\"numero_tva_intra\":\"FR96313029795\"}}}"));
    mockServer.verify(Duration.ofMillis(1000));
  }

  @DisplayName("Test businesses endpoint after startup")
  @Test
  public void callGetBusinesses() throws Exception {

    mvc.perform(get("/businesses")
                    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
                       .contentTypeCompatibleWith("application/hal+json"))
        .andExpect(jsonPath("$", hasKey("_embedded")))
        .andExpect(jsonPath("$._embedded", hasKey("businesses")))
        .andExpect(jsonPath("$._embedded.businesses", hasSize(1)))
        .andExpect(jsonPath("$._embedded.businesses[0].id", is("1522897774")))
        .andExpect(jsonPath("$._embedded.businesses[0].nic", is("00017")))
        .andExpect(jsonPath("$._embedded.businesses[0].creationDate", is("1978-01-01")))
        .andExpect(jsonPath("$._embedded.businesses[0].fullName", is("SOC EXPL PEPINIERES GAUDISSART")))
        .andExpect(jsonPath("$._embedded.businesses[0].address", is("261 Chemin des Colles 06140 Vence")))
        .andExpect(jsonPath("$._embedded.businesses[0].tvaNumber", is("FR96313029795")));
  }

  @DisplayName("Test sirets endpoint after startup")
  @Test
  public void callGetSirets() throws Exception {

    mvc.perform(get("/sirets")
                    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content()
                       .contentTypeCompatibleWith("application/hal+json"))
        .andExpect(jsonPath("$", hasKey("_embedded")))
        .andExpect(jsonPath("$._embedded", hasKey("sirets")))
        .andExpect(jsonPath("$._embedded.sirets", hasSize(1)))
        .andExpect(jsonPath("$._embedded.sirets[0].code", is("31302979500017")));
  }

}
