package com.intellias.advisory.greeting.util;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class FakerUtil {

    public String getEmail() {
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        return fakeValuesService.bothify("????##@gmail.com");
    }

    public String getFirstName() {
        return new Faker().name().firstName();

    }

    public String getLastName() {
        return new Faker().name().lastName();

    }
}
