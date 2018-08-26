package com.intellias.greeting.service.person;

import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.jpa.model.GreetingMessage;
import com.intellias.greeting.jpa.model.Person;
import com.intellias.greeting.jpa.repository.GreetingMessageRepository;
import com.intellias.greeting.jpa.repository.PersonRepository;
import com.intellias.greeting.service.Greeting;
import com.intellias.greeting.service.person.dto.PersonGreetingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class PersonGreetingService implements Greeting<PersonGreetingDto> {

    private final GreetingConfig greetingConfig;

    private final PersonRepository personRepository;

    private final GreetingMessageRepository greetingMessageRepository;

    public PersonGreetingDto greeting(String email) {

        return personRepository.findByEmail(email)
                .map(person -> PersonGreetingDto.builder()
                        .firstName(person.getFirstName())
                        .secondName(person.getLastName())
                        .message(person.getGreetingMessage()
                                .map(GreetingMessage::getMessage)
                                .orElse(greetingConfig.getGreeting()))
                        .build())
                .orElse(PersonGreetingDto.emptyGreeting(email));
    }

    public String updateGreeting(String email, String message) {
        return personRepository.findByEmail(email)
                .flatMap(Person::getGreetingMessage)
                .map(gMessage -> gMessage.updateMessage(message))
                .map(greetingMessageRepository::save)
                .map(greetingMessage -> format("New greeting message for person %s is %s", email, message))
                .orElse(String.format(GreetingVO.EMPTY, email));
    }
}
