package com.ifoodapi.domain.service;

import com.ifoodapi.api.exceptionhandler.model.ProblemType;
import com.ifoodapi.domain.entity.Cozinha;
import com.ifoodapi.domain.entity.Restaurante;
import com.ifoodapi.domain.repository.CozinhaRepository;
import com.ifoodapi.domain.repository.RestauranteRepository;
import com.ifoodapi.domain.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CozinhaServiceIT {

    public static final long COZINHA_ID_INEXISTENTE = 100000L;
    public static final String COZINHA_ID_INVALIDO = "aasda";
    public static final String NOME_COZINHA_CHINESA = "Chinesa";

    @Autowired
    private CozinhaService CozinhaService;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private Cozinha cozinhaAmericana;
    private Cozinha cozinhaChinesa;
    private Cozinha cozinhaEmUso;
    private String jsonCozinhaChinesa;
    private String jsonCozinhaComNomeEmBranco;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws JSONException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        this.jsonCozinhaChinesa = ResourceUtils.getContentFromResource("/json/cozinha-chinesa.json");
        this.jsonCozinhaComNomeEmBranco = ResourceUtils.getContentFromResource("/json/cozinha-em-branco.json");

        limpaTabela();
        preparaDados();
    }

    @Test
    public void deveRetornar200ECozinhaAmericanaEChinesa_QuandoConsultarTodasAsCozinhas() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("nome", hasItems(cozinhaAmericana.getNome(), cozinhaChinesa.getNome()));
    }

    @Test
    public void deveRetornar200ENomeCozinhaAmericana_QuandoConsultarCozinhaComIdDaCozinhaAmericana() {
        given()
            .pathParams("cozinhaId", this.cozinhaAmericana.getId())
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body(containsStringIgnoringCase(this.cozinhaAmericana.getNome()));
    }

    @Test
    public void deveRetonar201ENomeCozinhaChinesa_QuandoCriarUmaCozinhaChinesa() throws JSONException {

        given()
            .body(jsonCozinhaChinesa)
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value())
            .body(containsStringIgnoringCase(NOME_COZINHA_CHINESA));
    }

    @Test
    public void deveRetornar400_QuandoConsultaCozinhaComIdInvalido() {
        given()
                .pathParams("cozinhaId", COZINHA_ID_INVALIDO)
                .accept(ContentType.JSON)
                .when()
                .get("/{cozinhaId}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void deveRetornar400_QuandoTentarCriarCozinhaComNomeEmBranco() {
        given()
            .body(jsonCozinhaComNomeEmBranco)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(containsStringIgnoringCase(ProblemType.DADOS_INVALIDOS.getTitle()));
    }

    @Test
    public void deveRetornar400_QuandoTentarAtualizarCozinhaComNomeEmBranco() {
        given()
            .body(jsonCozinhaComNomeEmBranco)
            .pathParams("cozinhaId", this.cozinhaAmericana.getId())
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body(containsStringIgnoringCase(ProblemType.DADOS_INVALIDOS.getTitle()));
    }

    @Test
    public void deveRetornar409_QuandoTentarDeletarCozinhaEmUso() throws JSONException {
        given()
            .pathParams("cozinhaId", this.cozinhaEmUso.getId())
            .accept(ContentType.JSON)
        .when()
            .delete("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.CONFLICT.value())
            .body(containsStringIgnoringCase(ProblemType.ENTIDADE_EM_USO.getTitle()));
    }

    @Test
    public void deveRetornar404_QuandoConsultarCozinhaInexistente() {
        given()
            .pathParams("cozinhaId", COZINHA_ID_INEXISTENTE)
            .accept(ContentType.JSON)
        .when()
            .get("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornar404_QuandoTentarAtualizarCozinhaInexistente() {
        given()
            .body(jsonCozinhaChinesa)
            .pathParams("cozinhaId", COZINHA_ID_INEXISTENTE)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body(containsStringIgnoringCase(ProblemType.RECURSO_NAO_ENCONTRADO.getTitle()));
    }

    @Test
    public void deveRetornar404_QuandoTentarDeletarCozinhaInexistente() {
        given()
            .body(jsonCozinhaChinesa)
            .pathParams("cozinhaId", COZINHA_ID_INEXISTENTE)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .put("/{cozinhaId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .body(containsStringIgnoringCase(ProblemType.RECURSO_NAO_ENCONTRADO.getTitle()));
    }

    private void preparaDados() throws JSONException {
        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");

        Cozinha cozinhaChinesa = new Cozinha();
        cozinhaChinesa.setNome("Chinesa");

        Cozinha cozinhaEmUso = new Cozinha();
        cozinhaEmUso.setNome("Indiana");

        this.cozinhaAmericana = CozinhaService.save(cozinhaAmericana);
        this.cozinhaChinesa = CozinhaService.save(cozinhaChinesa);
        this.cozinhaEmUso = CozinhaService.save(cozinhaEmUso);
        criaRestauranteComCozinha(this.cozinhaEmUso);
    }

    private void criaRestauranteComCozinha(Cozinha cozinhaEmUso) throws JSONException {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Restaurante Lipe");
        restaurante.opening();
        restaurante.active();
        restaurante.setTaxaFrete(BigDecimal.valueOf(10.0));
        restaurante.setCozinha(this.cozinhaEmUso);

        restauranteRepository.save(restaurante);
    }

    private void limpaTabela() {
        restauranteRepository.deleteAll();
        cozinhaRepository.deleteAll();
    }
}
