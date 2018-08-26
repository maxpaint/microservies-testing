package com.intellias.advisory.greeting.jpa.repository;

import com.intellias.advisory.greeting.jpa.model.Person;
import com.intellias.advisory.greeting.util.FakerUtil;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

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

        entityManager.persist(person);

        Optional<Person> maybePeter = personRepository.findByEmail(person.getEmail());

        assertThat(maybePeter, is(Optional.of(person)));
    }

}