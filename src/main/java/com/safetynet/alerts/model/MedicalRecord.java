package com.safetynet.alerts.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value={ "age" }, allowGetters=true)
public class MedicalRecord implements Serializable {

    private String firstName;

    private String lastName;

    @JsonFormat(pattern = "MM/dd/yyyy", timezone = "Europe/Paris")
    private LocalDate birthdate;

    private List<String> medications;

    private List<String> allergies;

    public MedicalRecord update(MedicalRecord medicalrecord){
        this.birthdate = medicalrecord.getBirthdate();
        this.medications = medicalrecord.getMedications();
        this.allergies = medicalrecord.getAllergies();
        return medicalrecord;
    }

    /**
     * calculate age from birthdate
     * @return int
     */
    public int getAge() {
        return Period.between(birthdate, LocalDate.now()).getYears();
    }
}