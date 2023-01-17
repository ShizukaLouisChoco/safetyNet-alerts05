package com.safetynet.alerts.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * DTO for Person info with adults and children number
 */
@Data
@NoArgsConstructor
public class PersonInfosWithAdultsAndChildrenNumberDTO implements Serializable {
    private Set<PersonInfo> persons;

    private Long childNumber;
    private Long adultNumber;

    public PersonInfosWithAdultsAndChildrenNumberDTO(Set<PersonInfoDTO> personsList) {
        this.persons = personsList
                .stream()
                .map(PersonInfo::new)
                .collect(Collectors.toSet());
        this.childNumber = personsList
                .stream()
                .filter(PersonInfoDTO::isMinor)
                .count();
        this.adultNumber = personsList.size() - this.childNumber;
    }

    @Data
    @NoArgsConstructor
    static class PersonInfo {
        private String firstName;
        private String lastName;
        private String address;
        private String phone;

        public PersonInfo(PersonInfoDTO person) {
            this.firstName = person.getFirstName();
            this.lastName = person.getLastName();
            this.address = person.getAddress();
            this.phone = person.getPhone();
        }
    }
}