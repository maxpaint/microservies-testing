package com.intellias.advisory.greeting.jpa.repository;

import com.intellias.advisory.greeting.GreetingApplication;
import com.intellias.advisory.greeting.config.EmbeddedPostgresConfig;
import com.intellias.advisory.greeting.jpa.model.Person;
import com.intellias.advisory.greeting.util.FakerUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        GreetingApplication.class,
        EmbeddedPostgresConfig.class,
})
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @After
    public void tearDown() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchPerson() {
        Person person = Person.builder()
                .email(FakerUtil.getEmail())
                .firstName(FakerUtil.getFirstName())
                .lastName(FakerUtil.getLastName())
                .build();

        personRepository.save(person);

        Optional<Person> maybePerson = personRepository.findByEmail(person.getEmail());

        assertThat(maybePerson, is(Optional.of(person)));
    }

}