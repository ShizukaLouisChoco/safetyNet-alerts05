package com.safetynet.alerts.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  DTO for a child and this child's family member list
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildInfoWithFamilyDTO implements Serializable {

    private String firstName;
    private String lastName;
    private int age;
    private Set<Family> familyMembers;


    public ChildInfoWithFamilyDTO(PersonInfoDTO child, Set<PersonInfoDTO> personList) {
        this.firstName = child.getFirstName();
        this.lastName = child.getLastName();
        this.age = child.getAge();

        this.familyMembers = personList
                .stream()
                .filter(c -> !c.isMinor())
                .map(c -> new Family(c))
                .collect(Collectors.toSet());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Family {
        private String firstName;
        private String lastName;

        public Family(PersonInfoDTO family) {
            this.firstName = family.getFirstName();
            this.lastName = family.getLastName();
        }
    }
}