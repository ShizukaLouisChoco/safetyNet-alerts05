package com.safetynet.alerts.service;


import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Person;

import java.util.Optional;
import java.util.stream.Stream;

public interface PersonService {
    Optional<Person> findPersonByFirstNameAndLastName(final String firstName, final String lastName) ;
    Person savePerson(Person person) ;

    Person updatePerson(Person person) throws PersonNotFoundException;

    void deletePerson(String firstName, String lastName) ;

    Stream<Person> getAllPerson() ;
}