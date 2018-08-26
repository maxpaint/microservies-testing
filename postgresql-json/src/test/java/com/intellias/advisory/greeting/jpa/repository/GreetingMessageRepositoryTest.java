package com.intellias.advisory.greeting.jpa.repository;

import com.intellias.advisory.greeting.GreetingApplication;
import com.intellias.advisory.greeting.config.EmbeddedPostgresConfig;
import com.intellias.advisory.greeting.jpa.model.GreetingMessage;
import com.intellias.advisory.greeting.jpa.model.Person;
import com.intellias.advisory.greeting.util.FakerUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        GreetingApplication.class,
        EmbeddedPostgresConfig.class,
})
public class GreetingMessageRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GreetingMessageRepository greetingMessageRepository;

    @After
    public void tearDown() {
        personRepository.deleteAll();
    }


    @Test
    public void shouldUpdateGreetingMessage() {
        String message = "Test message";
        Person person = Person.builder()
                .email(FakerUtil.getEmail())
                .firstName(FakerUtil.getFirstName())
                .lastName(FakerUtil.getLastName())
                .build();

        GreetingMessage greetingMessage = GreetingMessage.builder()
                .message(message)
                .person(person)
                .build();

        person.setGreetingMessage(greetingMessage);

        personRepository.saveAndFlush(person);
        Optional<Person> maybePerson = personRepository.findByEmail(person.getEmail());
        assertThat(maybePerson.get().getEmail(), is(person.getEmail()));


        String newMessage = "New message";

        GreetingMessage updatedGreetingMessage = maybePerson
                .flatMap(Person::getGreetingMessage)
                .map(gMessage -> gMessage.updateMessage(newMessage))
                .map(greetingMessageRepository::saveAndFlush)
                .get();


        assertEquals(newMessage, updatedGreetingMessage.getMessage());
        assertEquals(1, greetingMessageRepository.findAll().size());
        assertEquals(newMessage, greetingMessageRepository.findAll().get(0).getMessage());

        /*assertEquals(newMessage, greetingMessageRepository.findAll().get(0)
                .getMessage().replaceAll("\"", ""));*/

    }

}