package com.intellias.greeting.api.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.lang.String.format;

@Builder
@RequiredArgsConstructor
@Getter
public class GreetingVO {

    public final static String EMPTY = "Who is this '%s' ? Please register in the service";
    private final String firstName;

    private final String secondName;

    private final String message;


    public static GreetingVO emptyGreeting(String email) {
        return GreetingVO
                .builder()
                .message(format(EMPTY, email))
                .build();
    }

}
