package com.safetynet.alerts.dto;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
/**
 * DTO for Person info
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoDTO implements Serializable {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String phone;
    private String email;
    private List<String> medications;
    private List<String> allergies;

    public PersonInfoDTO(Person person , MedicalRecord medicalRecord){
        this.lastName = person.getLastName();
        this.firstName = person.getFirstName();
        this.address = person.getAddress();
        this.age = medicalRecord.getAge();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
    /**
     * check age is under 18 yo or not
     * @return boolean
     */
    public boolean isMinor(){
        return age < 18;
    }
}