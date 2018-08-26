package com.intellias.greeting.api;

import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.jpa.model.GreetingMessage;
import com.intellias.greeting.jpa.model.Person;
import com.intellias.greeting.jpa.repository.GreetingMessageRepository;
import com.intellias.greeting.jpa.repository.PersonRepository;
import com.intellias.greeting.util.FakerUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class PersonControllerTest {

    private PersonController personController;

    private GreetingConfig greetingConfig = new GreetingConfig();

    @Mock
    private GreetingMessageRepository greetingMessageRepository;

    @Mock
    private PersonRepository personRepo;

    @Before
    public void setUp() {
        initMocks(this);
        ReflectionTestUtils.setField(greetingConfig, "greeting", "Hello");
        personController = new PersonController(greetingConfig, personRepo, greetingMessageRepository);
    }

    @Test
    public void shouldReturnGreetingVOForPerson() {
        Person person = Person.builder()
                .email(FakerUtil.getEmail())
                .firstName(FakerUtil.getFirstName())
                .lastName(FakerUtil.getLastName())
                .build();
        given(personRepo.findByEmail(person.getEmail()))
                .willReturn(Optional.of(person));

        GreetingVO greeting = personController.greeting(person.getEmail());

        assertThat(greeting.getFirstName(), is(person.getFirstName()));
        assertThat(greeting.getSecondName(), is(person.getLastName()));
        assertThat(greeting.getMessage(), is("Hello"));
    }

    @Test
    public void shouldReturnEmptyGreetingVOForPerson() {
        given(personRepo.findByEmail(anyString()))
                .willReturn(Optional.empty());

        String email = FakerUtil.getEmail();
        GreetingVO greeting = personController.greeting(email);

        assertThat(greeting.getMessage(), Matchers.is(String.format(GreetingVO.EMPTY, email)));
    }

    @Test
    public void shouldReturnUpdateMessageForPerson() {
        String message = "Test message";
        Person person = Person.builder()
                .email(FakerUtil.getEmail())
                .firstName(FakerUtil.getFirstName())
                .lastName(FakerUtil.getLastName())
                .greetingMessage(GreetingMessage.builder()
                        .message(message)
                        .build())
                .build();

        given(personRepo.findByEmail(person.getEmail()))
                .willReturn(Optional.of(person));

        given(greetingMessageRepository.save(any(GreetingMessage.class))).willAnswer(returnsFirstArg());

        String newMessage = "New message";

        String response = personController.updateGreeting(person.getEmail(), newMessage);

        assertThat(response, is(format("New greeting message for person %s is %s", person.getEmail(), newMessage)));
    }
}