package com.intellias.greeting.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.jpa.model.Person;
import com.intellias.greeting.jpa.repository.GreetingMessageRepository;
import com.intellias.greeting.jpa.repository.PersonRepository;
import com.intellias.greeting.util.FakerUtil;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonalControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GreetingMessageRepository greetingMessageRepository;

    @MockBean
    private PersonRepository personRepo;

    @MockBean
    private GreetingConfig greetingConfig;


    @SneakyThrows
    @Test
    public void shouldReturnGreetingVOForPerson() {
        Person person = Person.builder()
                .email(FakerUtil.getEmail())
                .firstName(FakerUtil.getFirstName())
                .lastName(FakerUtil.getLastName())
                .build();

        given(greetingConfig.getGreeting()).willReturn("Hello");

        given(personRepo.findByEmail(person.getEmail()))
                .willReturn(Optional.of(person));

        MvcResult result = this.mvc.perform(get("/person/greeting/" + person.getEmail()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        GreetingVO greeting = getContentAsObject(new ObjectMapper(), result, GreetingVO.class);


        assertThat(greeting.getFirstName(), is(person.getFirstName()));
        assertThat(greeting.getSecondName(), is(person.getLastName()));
        assertThat(greeting.getMessage(), is("Hello"));
    }


    private <T> T getContentAsObject(ObjectMapper objectMapper, MvcResult result, Class<T> clazz) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }


}
