package com.intellias.greeting.util;

import com.intellias.greeting.jpa.model.Person;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonHelper {


    public Person getPerson() {
        return Person.builder()
                .email(FakerUtil.getEmail())
                .firstName(FakerUtil.getFirstName())
                .lastName(FakerUtil.getLastName())
                .build();
    }
}
