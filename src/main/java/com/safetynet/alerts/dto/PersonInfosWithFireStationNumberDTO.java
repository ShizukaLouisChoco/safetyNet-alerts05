package com.safetynet.alerts.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * DTO for person Info with Fire station number
 */

@Data
@NoArgsConstructor
public class PersonInfosWithFireStationNumberDTO implements Serializable {

    private Set<PersonInfo> persons;
    private Integer fireStationNumber;

    public PersonInfosWithFireStationNumberDTO(Set<PersonInfoDTO> personList, Integer fireStationNumber){
        persons = personList
                .stream()
                .map(PersonInfo::new)
                .collect(Collectors.toSet());
        this.fireStationNumber = fireStationNumber;
    }
    @Data
    @NoArgsConstructor
    static class PersonInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private List<String> medications;
        private List<String> allergies;

        public PersonInfo(PersonInfoDTO person){
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.phone = person.getPhone();
            this.age = person.getAge();
            this.medications = person.getMedications();
            this.allergies = person.getAllergies();
        }
    }


}