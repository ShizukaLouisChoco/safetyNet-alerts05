package com.safetynet.alerts.repository;
import com.safetynet.alerts.model.Person;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface PersonRepository {
    Optional<Person> findPersonByFirstNameAndLastName(String firstName, String lastName);
    Stream<Person> findPersonsByFirstNameOrLastName(String firstName, String lastName);

    void deleteByFirstNameAndLastName(String firstName, String lastName) ;


    void updatePerson(Person updatedPerson) ;

    Person savePerson(Person person) ;

    Stream<Person> getAllPerson() ;

    Stream<Person> getAllByAddress(String address);

    Stream<Person> getPersonByAddressList(Set<String> fireStationAddressList);

    Stream<Person> getAllPersonsByCity(String city);
}