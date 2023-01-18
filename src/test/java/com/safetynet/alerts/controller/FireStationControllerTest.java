package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.FireStation;
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
public class FireStationControllerTest extends AbstractControllerTest {
    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    public void init() throws IOException {
        createDataStorage();
    }

    @Test
    @DisplayName("Test GetMapping")
    public void getMappingFireStationTest() throws Exception {
        //WHEN
        final var result = mockMvc.perform(get("/firestations"));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, List.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getClass()).isEqualTo(ArrayList.class);
            assertThat(r.toString()).isEqualTo("[{address=address1, station=101}, {address=address5, station=101}, {address=address2, station=102}, {address=address4, station=104}, {address=address6, station=105}]");
        });
        assertThat(response.stream().count()).isEqualTo(5L);
    }

    @Test
    @DisplayName("Test PostMapping")
    public void postMappingFireStationTest() throws Exception {
        //GIVEN
        FireStation fireStation = new FireStation("address6",105);
        //WHEN
        final var result = mockMvc.perform(post("/firestation")
                .content(asJsonString(fireStation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, FireStation.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getAddress()).isEqualTo("address6");
            assertThat(r.getStation()).isEqualTo(105L);
            assertThat(r.toString()).isEqualTo("FireStation(address=address6, station=105)");
        });
    }

    @Test
    @DisplayName("Test PutMapping")
    public void putMappingFireStationTest() throws Exception {
        //GIVEN
        FireStation fireStation = new FireStation("address6",105);
        String address = "address4";
        //WHEN
        final var result = mockMvc.perform(put("/firestation")
                .param("address",address)
                .content(asJsonString(fireStation))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, FireStation.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getAddress()).isEqualTo("address6");
            assertThat(r.getStation()).isEqualTo(105L);
            assertThat(r.toString()).isEqualTo("FireStation(address=address6, station=105)");
        });
    }


    @Test
    @DisplayName("Test DeleteMapping")
    public void deleteMappingFireStationTest() throws Exception {
        //GIVEN
        String address = "address3";
        //WHEN
        final var result = mockMvc.perform(delete("/firestation")
                .param("address",address));
        //THEN
        result.andExpect(status().isOk());

    }

}