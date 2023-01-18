package com.safetynet.alerts.controller;


import com.safetynet.alerts.model.MedicalRecord;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest extends AbstractControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init() throws IOException {
        createDataStorage();
    }
    @Test
    @DisplayName("Test GetMapping")
    public void getMappingMedicalRecordTest() throws Exception {
        //WHEN
        final var result = mockMvc.perform(get("/medicalrecords"));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, List.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getClass()).isEqualTo(ArrayList.class);
            assertThat(r.toString()).isEqualTo("[{firstName=adult1, lastName=adult1, birthdate=03/06/1984, medications=[aznol:350mg, hydrapermazol:100mg], allergies=[nillacilan], age=38}, {firstName=child1, lastName=child1, birthdate=09/06/2017, medications=[], allergies=[], age=5}, {firstName=adult2, lastName=adult2, birthdate=09/06/2000, medications=[], allergies=[shellfish], age=22}, {firstName=adult3, lastName=adult3, birthdate=03/06/1988, medications=[aznol:60mg, hydrapermazol:900mg, pharmacol:5000mg, terazine:500mg], allergies=[peanut, shellfish, aznol], age=34}, {firstName=adult4, lastName=adult4, birthdate=03/06/1985, medications=[], allergies=[], age=37}, {firstName=child2, lastName=child2, birthdate=03/06/2017, medications=[], allergies=[], age=5}, {firstName=adult5, lastName=adult5, birthdate=08/06/1945, medications=[tradoxidine:400mg], allergies=[], age=77}, {firstName=adult6, lastName=adult6, birthdate=03/06/1987, medications=[], allergies=[], age=35}]");
        });
        assertThat(response.stream().count()).isEqualTo(8L);
    }

    @Test
    @DisplayName("Test PostMapping")
    public void postMappingMedicalRecordTest() throws Exception {
        //GIVEN
        LocalDate birthdate = LocalDate.of(1990,2,3);
        List<String> medications = new ArrayList<>();
        medications.add("medication1");
        List<String> allergies = new ArrayList<>();
        allergies.add("allergy1");
        MedicalRecord medicalRecord= new MedicalRecord("adult7","adult7",birthdate,medications,allergies);
        //WHEN
        final var result = mockMvc.perform(post("/medicalrecord")
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, MedicalRecord.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getFirstName()).isEqualTo(medicalRecord.getFirstName());
            assertThat(r.getLastName()).isEqualTo(medicalRecord.getLastName());
            assertThat(r.getBirthdate()).isEqualTo(medicalRecord.getBirthdate());
            assertThat(r.getMedications()).isEqualTo(medicalRecord.getMedications());
            assertThat(r.getAllergies()).isEqualTo(medicalRecord.getAllergies());
            assertThat(r.toString()).isEqualTo("MedicalRecord(firstName=adult7, lastName=adult7, birthdate=1990-02-03, medications=[medication1], allergies=[allergy1])");
        });

    }

    @Test
    @DisplayName("Test PutMapping")
    public void putMappingMedicalRecordTest() throws Exception {
        //GIVEN
        MedicalRecord medicalRecord= new MedicalRecord("child1","child1",LocalDate.of(2017,9,6),List.of(),List.of("penuts"));
        //WHEN
        final var result = mockMvc.perform(put("/medicalrecord")
                .param("firstName",medicalRecord.getFirstName())
                .param("lastName",medicalRecord.getLastName())
                .content(asJsonString(medicalRecord))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        //THEN
        result.andExpect(status().isOk());

        final var response = toObject(result, MedicalRecord.class);
        assertThat(response).satisfies(r -> {
            assertThat(r.getFirstName()).isEqualTo(medicalRecord.getFirstName());
            assertThat(r.getLastName()).isEqualTo(medicalRecord.getLastName());
            assertThat(r.getBirthdate()).isEqualTo(medicalRecord.getBirthdate());
            assertThat(r.getMedications()).isEqualTo(medicalRecord.getMedications());
            assertThat(r.getAllergies()).isEqualTo(medicalRecord.getAllergies());
            assertThat(r.toString()).isEqualTo("MedicalRecord(firstName=child1, lastName=child1, birthdate=2017-09-06, medications=[], allergies=[penuts])");
        });
    }


    @Test
    @DisplayName("Test DeleteMapping")
    public void deleteMappingMedicalRecordTest() throws Exception {
        //GIVEN
        String firstName = "adult5";
        String lastName = "adult5";
        //WHEN
        final var result = mockMvc.perform(delete("/medicalrecord")
                .param("firstName",firstName)
                .param("lastName",lastName));
        //THEN
        result.andExpect(status().isOk());

    }

}