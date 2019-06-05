package tqs.api;

import static org.junit.Assert.*;

import org.junit.Test;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tqs.api.models.Cidade;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@LocalServerPort
    private int port;

    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    Requests requestsService;

    @Test
    public void testarDevolverCidadeResponse()
    {
    	
    	Response response = given().port(port).contentType("application/json").accept("application/json")
    								.when().get("/devolverCidade/Aveiro").then().statusCode(200)
    								.extract().response();
    	assertNotNull(response);
    }
    
    @Test
    public void testarDevolverCidade() {

        Cidade testCidade = new Cidade("California", 40, -112);
        when(requestsService.devolverCidade("California")).thenReturn(testCidade);

        get(uri + "/devolverCidade/" + testCidade.getNome()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("nome", equalTo(testCidade.getNome()))
            .body("latitude", notNullValue());

        Cidade result = get(uri + "/devolverCidade/" + testCidade.getNome()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .as(Cidade.class);
        assertEquals(result.getNome(), testCidade.getNome());

        String responseString = get(uri + "/devolverCidade/" + testCidade.getNome()).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .asString();
        assertThat(responseString).isNotEmpty();
        
    }

}
