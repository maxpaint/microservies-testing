package com.intellias.greeting.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.lang.String.format;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GreetingDto {

    public final static String EMPTY = "Who is this '%s' ? Please register in the service";

    private String firstName;

    private String secondName;

    private String message;

    public static GreetingDto emptyGreeting(String email) {
        return GreetingDto
                .builder()
                .message(format(EMPTY, email))
                .build();
    }
}
