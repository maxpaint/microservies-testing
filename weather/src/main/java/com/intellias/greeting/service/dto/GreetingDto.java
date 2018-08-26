package com.intellias.greeting.service.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GreetingDto {

    private String firstName;

    private String secondName;

    private String message;

    private String weatherSummary;
}
