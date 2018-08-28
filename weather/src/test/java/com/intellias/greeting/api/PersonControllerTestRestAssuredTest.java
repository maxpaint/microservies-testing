package com.intellias.greeting.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.config.WeatherServiceConfig;
import com.intellias.greeting.jpa.model.GreetingMessage;
import com.intellias.greeting.jpa.model.Person;
import com.intellias.greeting.jpa.repository.PersonRepository;
import com.intellias.greeting.service.weather.model.CurrentlyWeather;
import com.intellias.greeting.service.weather.model.Weather;
import com.intellias.greeting.test.config.CachedContext;
import com.intellias.greeting.util.FakerUtil;
import com.intellias.greeting.util.PersonHelper;
import lombok.SneakyThrows;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PersonControllerTestRestAssuredTest extends CachedContext {

    private MockRestServiceServer mockServer;

    @Autowired
    private GreetingConfig greetingConfig;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WeatherServiceConfig config;

    @LocalServerPort
    private Integer serverPort;

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);

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

        mockServer.expect(once(), requestTo(config.getUrl()))
                .andRespond(withSuccess(objectMapper.writeValueAsString(weather), APPLICATION_JSON_UTF8));


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

    @SneakyThrows
    @Test
    public void shouldReturnEmptyGreetingVOForPerson() {
        String weatherSummary = "Partly Cloudy";
        CurrentlyWeather currentlyWeather = CurrentlyWeather.builder()
                .summary(weatherSummary)
                .build();
        Weather weather = Weather.builder()
                .currently(currentlyWeather)
                .build();
        mockServer.expect(once(), requestTo(config.getUrl()))
                .andRespond(withSuccess(objectMapper.writeValueAsString(weather), APPLICATION_JSON_UTF8));

        String email = FakerUtil.getEmail();

        // @formatter:off
        given()
                .port(serverPort)
                .contentType(JSON)
                .pathParam("email", email)
                .when()
                .get("/person/greeting/{email}")
                .then()
                .statusCode(200)
                .body("message", equalToIgnoringCase(format(GreetingVO.EMPTY, email)))
        ;

        // @formatter:on

    }

    @SneakyThrows
    @Test
    public void shouldReturnUpdateMessageForPerson() {
        String weatherSummary = "Partly Cloudy";
        CurrentlyWeather currentlyWeather = CurrentlyWeather.builder()
                .summary(weatherSummary)
                .build();
        Weather weather = Weather.builder()
                .currently(currentlyWeather)
                .build();
        mockServer.expect(once(), requestTo(config.getUrl()))
                .andRespond(withSuccess(objectMapper.writeValueAsString(weather), APPLICATION_JSON_UTF8));

        Person person = PersonHelper.getPerson();

        String message = "Test message";

        person.setGreetingMessage(GreetingMessage.builder()
                .message(message)
                .person(person)
                .build());

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
                .body("message", equalToIgnoringCase("\"" + message + "\""))
        ;

        // @formatter:on

        String newMessage = "Test new message";
        String result = format("New greeting message for person %s is %s", person.getEmail(), newMessage);

        // @formatter:off
        given()
                .port(serverPort)
                .contentType(JSON)
                .pathParam("email", person.getEmail())
                .pathParam("message", newMessage)
                .when()
                .put("/person/greeting/{email}/{message}")
                .then()
                .statusCode(200)
                .content(equalToIgnoringCase(result))
        ;

        // @formatter:on

    }
}
