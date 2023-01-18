package com.safetynet.alerts.controller;

import com.safetynet.alerts.dto.ChildInfoWithFamilyDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonInfosWithAdultsAndChildrenNumberDTO;
import com.safetynet.alerts.dto.PersonInfosWithFireStationNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EndpointControllerTest extends AbstractControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws IOException {
        createDataStorage();
    }

    @Test
    @DisplayName("Test GetMapping firestation?stationNumber=1")
    public void getMappingGetPersonInfosWithAdultAndChildrenNumberTest() throws Exception {
        //GIVEN
        final var  stationNumber = "101";

        //WHEN
        final var result = mockMvc.perform(get("/firestation")
                .param("stationNumber", stationNumber)
        );

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, PersonInfosWithAdultsAndChildrenNumberDTO.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getPersons()).hasSize(3);
            assertThat(r.getAdultNumber()).isEqualTo(2L);
            assertThat(r.getChildNumber()).isEqualTo(1L);
        });
    }

    @Test
    @DisplayName("Test GetMapping /childAlert?address=address1")
    public void getMappingGetChildrenInfosWithFamilyByAddressTest() throws Exception {
        //GIVEN
        final var  address = "address1";
        final var expectedResponse =  List.of(new ChildInfoWithFamilyDTO("child1", "child1", 5, Set.of(new ChildInfoWithFamilyDTO.Family("adult1", "adult1")) ));

        //WHEN
        final var result = mockMvc.perform(get("/childAlert")
                .param("address", address)
        );

        //THEN
        result.andExpect(status().isOk());

        final var response = toList(result, ChildInfoWithFamilyDTO.class);

        assertThat(response).containsExactlyInAnyOrderElementsOf(expectedResponse);

    }

    @Test
    @DisplayName("Test GetMapping /phoneAlert?firestation=101")
    public void getMappingPhoneNumberSetByFireStationNumberTest() throws Exception {
        //GIVEN
        final var  stationNumber = "101";
        final var expectedResponse = Set.of("841-874-6512","841-874-7458");

        //WHEN
        final var result = mockMvc.perform(get("/phoneAlert")
                .param("firestation", stationNumber)
        );

        //THEN
        result.andExpect(status().isOk());

        final var response = toList(result, String.class);

        assertThat(response).containsExactlyInAnyOrderElementsOf(expectedResponse);

    }


    @Test
    @DisplayName("Test GetMapping /fire?address=address1")
    public void getMappingGetPersonInfosByAddressTest() throws Exception {
        //GIVEN
        final var  address = "address1";

        //WHEN
        final var result = mockMvc.perform(get("/fire")
                .param("address", address)
        );

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, PersonInfosWithFireStationNumberDTO.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getPersons()).hasSize(2);
            assertThat(r.getFireStationNumber()).isEqualTo(101L);
        });
    }

    @Test
    @DisplayName("Test GetMapping /flood/stations?stations=101,102")
    public void getMappingGetOrderedPersonInfoByStationNumbersTest() throws Exception {
        //GIVEN
        final var stationNumber = "101,102";

        //WHEN
        final var result = mockMvc.perform(get("/flood/stations")
                .param("stations", stationNumber)
        );

        //THEN
        result.andExpect(status().isOk());

        final Map<String, List<PersonInfoDTO>> response = toObject(result, Map.class);

        assertThat(response).hasSize(3);
    }

    @Test
    @DisplayName("Test GetMapping /personInfo?firstName=firstName1&lastName=lastName1")
    public void getMappingGetPersonsByFirstNameAndLastNameTest() throws Exception {
        //GIVEN
        final var  firstName = "adult1";
        final var  lastName = "adult1";
        final var expectedResponse = List.of(new PersonInfoDTO(
                firstName,
                lastName ,
                "address1",
                38,
                "841-874-6512",
                "jaboyd@email.com",
                List.of("aznol:350mg", "hydrapermazol:100mg"),
                List.of("nillacilan")));

        //WHEN
        final var result = mockMvc.perform(get("/personInfo")
                .param("firstName", firstName)
                .param("lastName", lastName)
        );

        //THEN
        result.andExpect(status().isOk());

        final var response = toList(result, PersonInfoDTO.class);
        assertThat(response).containsExactlyInAnyOrderElementsOf(expectedResponse);
    }


    @DisplayName("Test GetMapping /communityEmail?city=city1")
    @Test
    public void getMappingGetEmailSetByCityTest() throws Exception {
        //GIVEN
        final var city = "Culver";
        final var expectedResponse = List.of("ggterzps@email.com", "gramps@email.com", "soph@email.com", "jaboyd@email.com", "zarc@email.com", "ward@email.com");

        //WHEN
        final var result = mockMvc.perform(get("/communityEmail")
                .param("city", city)
        );

        //THEN
        result.andExpect(status().isOk());

        final var response = toList(result, String.class);
        assertThat(response).containsExactlyInAnyOrderElementsOf(expectedResponse);
    }
}