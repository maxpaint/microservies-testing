package com.intellias.greeting.service.person.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.lang.String.format;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PersonGreetingDto {

    public final static String EMPTY = "Who is this '%s' ? Please register in the service";

    private String firstName;
    private String secondName;
    private String message;

    public static PersonGreetingDto emptyGreeting(String email) {
        return PersonGreetingDto
                .builder()
                .message(format(EMPTY, email))
                .build();
    }
}
