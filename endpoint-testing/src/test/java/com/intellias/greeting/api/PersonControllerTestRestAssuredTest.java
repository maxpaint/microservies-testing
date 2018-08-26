package com.intellias.greeting.api;

import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.jpa.model.GreetingMessage;
import com.intellias.greeting.jpa.model.Person;
import com.intellias.greeting.jpa.repository.PersonRepository;
import com.intellias.greeting.test.config.CachedContext;
import com.intellias.greeting.util.FakerUtil;
import com.intellias.greeting.util.PersonHelper;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;

public class PersonControllerTestRestAssuredTest extends CachedContext {

    @Autowired
    private GreetingConfig greetingConfig;

    @Autowired
    private PersonRepository personRepository;

    @LocalServerPort
    private Integer serverPort;

    @After
    public void tearDown() {
        personRepository.deleteAll();
    }


    @Test
    public void shouldReturnGreetingVOForPerson() {
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
        ;

        // @formatter:on

    }

    @Test
    public void shouldReturnEmptyGreetingVOForPerson() {
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

    @Test
    public void shouldReturnUpdateMessageForPerson() {
        Person person = PersonHelper.getPerson();

        String message = "Default message";

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
                .content(equalToIgnoringCase(format("New greeting message for person %s is %s", person.getEmail(), newMessage)))
        ;

    }
}
