package com.intellias.greeting.api.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.lang.String.format;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GreetingVO {

    public final static String EMPTY = "Who is this '%s' ? Please register in the service";
    private  String firstName;
    private  String secondName;
    private  String message;


    public static GreetingVO emptyGreeting(String email) {
        return GreetingVO
                .builder()
                .message(format(EMPTY, email))
                .build();
    }

}
