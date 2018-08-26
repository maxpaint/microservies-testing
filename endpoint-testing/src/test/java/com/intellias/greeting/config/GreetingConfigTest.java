package com.intellias.greeting.config;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GreetingConfigTest extends CachedContext {

    @Autowired
    private GreetingConfig greetingConfig;

    @Test
    public void shouldReturnDefaultGreetingMessage() {
        assertThat("Hello !", is(greetingConfig.getGreeting()));
    }
}