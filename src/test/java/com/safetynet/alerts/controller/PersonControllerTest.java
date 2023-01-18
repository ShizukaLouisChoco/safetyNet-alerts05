package com.safetynet.alerts.controller;

import com.safetynet.alerts.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws IOException {
        createDataStorage();
    }
    @Test
    @DisplayName("Test GetMapping")
    public void getMappingPersonsTest() throws Exception {
        //WHEN
        final var result = mockMvc.perform(get("/persons"));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, List.class);

        assertThat(response).satisfies(r -> {
            assertThat(r.getClass()).isEqualTo(ArrayList.class);
            assertThat(r.toString()).isEqualTo("[{firstName=adult1, lastName=adult1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com}, {firstName=child1, lastName=child1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com}, {firstName=adult2, lastName=adult2, address=address2, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com}, {firstName=adult3, lastName=adult3, address=address3, city=Culver, zip=97451, phone=841-874-7878, email=soph@email.com}, {firstName=adult4, lastName=adult4, address=address3, city=Culver, zip=97451, phone=841-874-7512, email=ward@email.com}, {firstName=child2, lastName=child2, address=address3, city=Culver, zip=97451, phone=841-874-7512, email=zarc@email.com}, {firstName=adult5, lastName=adult5, address=address4, city=Culver, zip=97451, phone=841-874-7458, email=gramps@email.com}, {firstName=adult6, lastName=adult6, address=address5, city=Culver, zip=97451, phone=841-874-7458, email=ggterzps@email.com}]");
        });
        assertThat(response.stream().count()).isEqualTo(8L);
    }


    @Test
    @DisplayName("Test PostMapping")
    public void postMappingPersonTest() throws Exception {
        //GIVEN
        Person person = new Person("adult7", "adult7","address7","city7","zip7","phone7","email7");
        //WHEN
        final var result = mockMvc.perform(post("/person")
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, Person.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getFirstName()).isEqualTo(person.getFirstName());
            assertThat(r.getLastName()).isEqualTo(person.getLastName());
            assertThat(r.getAddress()).isEqualTo(person.getAddress());
            assertThat(r.getCity()).isEqualTo(person.getCity());
            assertThat(r.getZip()).isEqualTo(person.getZip());
            assertThat(r.getPhone()).isEqualTo(person.getPhone());
            assertThat(r.getEmail()).isEqualTo(person.getEmail());
            assertThat(r.toString()).isEqualTo("Person(firstName=adult7, lastName=adult7, address=address7, city=city7, zip=zip7, phone=phone7, email=email7)");
        });

    }

    @Test
    @DisplayName("Test PutMapping")
    public void putMappingPersonTest() throws Exception {
        //GIVEN
        Person person = new Person("adult1", "adult1","address5","city7","zip7","phone7","email7");
        //WHEN
        final var result = mockMvc.perform(put("/person")
                .param("firstName", person.getFirstName())
                .param("lastName",person.getLastName())
                .content(asJsonString(person))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, Person.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getFirstName()).isEqualTo(person.getFirstName());
            assertThat(r.getLastName()).isEqualTo(person.getLastName());
            assertThat(r.getAddress()).isEqualTo(person.getAddress());
            assertThat(r.getCity()).isEqualTo(person.getCity());
            assertThat(r.getZip()).isEqualTo(person.getZip());
            assertThat(r.getPhone()).isEqualTo(person.getPhone());
            assertThat(r.getEmail()).isEqualTo(person.getEmail());
            assertThat(r.toString()).isEqualTo("Person(firstName=adult1, lastName=adult1, address=address5, city=city7, zip=zip7, phone=phone7, email=email7)");
        });
    }


    @Test
    @DisplayName("Test DeleteMapping")
    public void deleteMappingPersonTest() throws Exception {
        //GIVEN
        Person person = new Person("adult3", "adult3","address3","city7","zip7","phone7","email7");
        //WHEN
        final var result = mockMvc.perform(delete("/person")
                .param("firstName", person.getFirstName())
                .param("lastName",person.getLastName()));
        //THEN
        result.andExpect(status().isOk());

    }
}