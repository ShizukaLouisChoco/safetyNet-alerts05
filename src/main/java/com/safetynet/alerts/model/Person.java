package com.safetynet.alerts.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String zip;

    private String phone;

    private String email;

    public Person update(Person person){
        this.address = person.getAddress();
        this.city = person.getCity();
        this.zip = person.getZip();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        return person;
    }
}