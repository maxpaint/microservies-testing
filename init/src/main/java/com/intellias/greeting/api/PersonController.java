package com.intellias.greeting.api;

import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.jpa.repository.GreetingMessageRepository;
import com.intellias.greeting.jpa.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/person/greeting")
public class PersonController {

    private final GreetingConfig greetingConfig;

    private final PersonRepository personRepository;

    private final GreetingMessageRepository greetingMessageRepository;

    @GetMapping("/{email}")
    public GreetingVO greeting(@PathVariable String email) {
        return personRepository.findByEmail(email)
                .map(person -> GreetingVO.builder()
                        .firstName(person.getFirstName())
                        .secondName(person.getLastName())
                        .message(greetingConfig.getGreeting())
                        .build())
                .orElse(GreetingVO.emptyGreeting(email));
    }

    /*@GetMapping("/{email}")
    public GreetingVO greeting(@PathVariable String email) {
        return personRepository.findByEmail(email)
                .map(person -> GreetingVO.builder()
                        .firstName(person.getFirstName())
                        .secondName(person.getLastName())
                        .message(person.getGreetingMessage()
                                .map(GreetingMessage::getMessage)
                                .orElse(greetingConfig.getGreeting()))
                        .build())
                .orElse(GreetingVO.emptyGreeting(email));
    }*/


    /*@PutMapping("/{email}/{message}")
    public String updateGreeting(@PathVariable String email, @PathVariable String message) {
        return personRepository.findByEmail(email)
                .flatMap(Person::getGreetingMessage)
                .map(gMessage -> gMessage.updateMessage(message))
                .map(greetingMessageRepository::save)
                .map(greetingMessage -> format("New greeting message for person %s is %s", email, message))
                .orElse(String.format(GreetingVO.EMPTY, email));
    }*/
}
