package com.intellias.greeting.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellias.greeting.config.GreetingConfig;
import com.intellias.greeting.jpa.repository.PersonRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonalControllerMockMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonRepository personRepo;

    @MockBean
    private GreetingConfig greetingConfig;


    /*@SneakyThrows
    @Test
    public void shouldReturnGreetingVOForPerson() {
        Person person = Person.builder()
                .email(FakerUtil.getEmail())
                .firstName(FakerUtil.getFirstName())
                .lastName(FakerUtil.getLastName())
                .build();

        String message = "Hello test";
        given(greetingConfig.getGreeting()).willReturn(message);

        given(personRepo.findByEmail(person.getEmail()))
                .willReturn(Optional.of(person));

        MvcResult result = this.mvc.perform(get("/person/greeting/" + person.getEmail()).accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        GreetingVO greeting = getContentAsObject(new ObjectMapper(), result, GreetingVO.class);


        assertThat(greeting.getFirstName(), is(person.getFirstName()));
        assertThat(greeting.getSecondName(), is(person.getLastName()));
        assertThat(greeting.getMessage(), is(message));
    }*/


    private <T> T getContentAsObject(ObjectMapper objectMapper, MvcResult result, Class<T> clazz) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }


}
