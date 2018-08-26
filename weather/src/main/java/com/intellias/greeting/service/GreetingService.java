package com.intellias.greeting.service;

import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.jpa.model.GreetingMessage;
import com.intellias.greeting.jpa.model.Person;
import com.intellias.greeting.jpa.repository.GreetingMessageRepository;
import com.intellias.greeting.jpa.repository.PersonRepository;
import com.intellias.greeting.service.dto.GreetingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@RequiredArgsConstructor
@Service
public class GreetingService {

    private final GreetingConfig greetingConfig;

    private final PersonRepository personRepository;

    private final GreetingMessageRepository greetingMessageRepository;

    public GreetingDto greeting(String email) {
        return personRepository.findByEmail(email)
                .map(person -> GreetingDto.builder()
                        .firstName(person.getFirstName())
                        .secondName(person.getLastName())
                        .message(person.getGreetingMessage()
                                .map(GreetingMessage::getMessage)
                                .orElse(greetingConfig.getGreeting()))
                        .build())
                .orElse(GreetingDto.emptyGreeting(email));
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
