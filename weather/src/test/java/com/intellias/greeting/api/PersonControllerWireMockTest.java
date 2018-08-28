package com.intellias.greeting.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.config.WeatherServiceConfig;
import com.intellias.greeting.jpa.model.Person;
import com.intellias.greeting.jpa.repository.PersonRepository;
import com.intellias.greeting.service.weather.model.CurrentlyWeather;
import com.intellias.greeting.service.weather.model.Weather;
import com.intellias.greeting.test.config.CachedContext;
import com.intellias.greeting.util.PersonHelper;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

public class PersonControllerWireMockTest extends CachedContext {

    @Autowired
    private GreetingConfig greetingConfig;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherServiceConfig config;

    @LocalServerPort
    private Integer serverPort;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8888);

    @Before
    public void init() {
        config.setUrl("http://localhost:8888/");

    }

    @After
    public void tearDown() {
        personRepository.deleteAll();
    }


    @SneakyThrows
    @Test
    public void shouldReturnGreetingVOForPerson() {
        String weatherSummary = "Partly Cloudy";
        CurrentlyWeather currentlyWeather = CurrentlyWeather.builder()
                .summary(weatherSummary)
                .build();

        Weather weather = Weather.builder()
                .currently(currentlyWeather)
                .build();

        stubFor(get(urlEqualTo("/" + config.getToken() + "/" + config.getCoordinates()))
                .willReturn(aResponse()
                        .withHeader("Content-Type", JSON.getContentTypeStrings()[0])
                        .withBody(objectMapper.writeValueAsString(weather))));


        Person person = PersonHelper.getPerson();
        personRepository.saveAndFlush(person);

        // @formatter:off
        given()
                .port(serverPort)
                .contentType(JSON)
                .pathParam("email", person.getEmail())
                .when()
                .get("/person/greeting/{email}")
                .then()
                .statusCode(200)
                .body("firstName", is(person.getFirstName()))
                .body("secondName", is(person.getLastName()))
                .body("message", equalToIgnoringCase(greetingConfig.getGreeting()))
                .body("weatherSummary", equalToIgnoringCase(weatherSummary))
        ;
        // @formatter:on

    }
}
