package com.intellias.advisory.greeting.jpa.repository;

import com.intellias.advisory.greeting.api.vo.GreetingVO;
import com.intellias.advisory.greeting.jpa.model.GreetingMessage;
import com.intellias.advisory.greeting.jpa.model.Person;
import com.intellias.advisory.greeting.util.FakerUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GreetingMessageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

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
                .greetingMessage(GreetingMessage.builder()
                        .message(message)
                        .build())
                .build();

        entityManager.persist(person);

        Optional<Person> maybePerson = personRepository.findByEmail(person.getEmail());

        assertThat(maybePerson, is(Optional.of(person)));


        String newMessage = "New message";

        String response = maybePerson
                .flatMap(Person::getGreetingMessage)
                .map(gMessage -> gMessage.updateMessage(newMessage))
                .map(greetingMessageRepository::save)
                .map(greetingMessage -> format("New greeting message for person %s is %s", person.getEmail(), newMessage))
                .orElse(format(GreetingVO.EMPTY, person.getEmail()));

        assertThat(format("New greeting message for person %s is %s", person.getEmail(), newMessage), is(response));

        assertEquals(1, greetingMessageRepository.findAll().size());
        assertEquals(newMessage, greetingMessageRepository.findAll().get(0).getMessage());


    }

}