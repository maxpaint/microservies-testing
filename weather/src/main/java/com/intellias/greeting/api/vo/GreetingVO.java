package com.intellias.greeting.api.vo;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GreetingVO {

    public final static String EMPTY = "Who is this '%s' ? Please register in the service";

    private String firstName = "";
    private String secondName = "";
    private String message = "";

}
