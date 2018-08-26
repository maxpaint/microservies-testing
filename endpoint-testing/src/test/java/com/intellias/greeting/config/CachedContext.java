package com.intellias.greeting.config;

import com.intellias.greeting.GreetingApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        GreetingApplication.class,
        EmbeddedPostgresConfig.class,
},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CachedContext {

}
