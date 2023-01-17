package com.safetynet.alerts.service;


import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.impl.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @InjectMocks
    PersonServiceImpl personService;
    @Mock
    private  PersonRepository personRepository;
    List<Person> personList;
    Person person,person2;

    @BeforeEach
    public void init(){
        personList = new ArrayList<>();
        person = new Person("firstName1", "lastName1","address1","city1","zip1","phone1","email1");
        person2 = new Person("firstName2", "lastName2","address2","city2","zip2","phone2","email2");
        personList.add(person);
        personList.add(person2);
    }

    @Test
    @DisplayName("findPersonByFirstNameAndLastName calls personRepository.findPersonByFirstNameAndLastName()")
    public void testFindPersonByFirstNameAndLastName()  {
        //GIVEN
        //WHEN
        personService.findPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName());
        //THEN
        verify(personRepository,times(1)).findPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName());
    }

    @Test
    @DisplayName("savePerson calls personRepository.savePerson()")
    public void savePersonTest() throws IOException {
        //WHEN
        personService.savePerson(person2);
        //THEN
        verify(personRepository,times(1)).savePerson(person2);
    }

    @Test
    @DisplayName("savePerson calls personRepository.savePerson()")
    public void updatePersonTest() {
        Person requestBodyPerson = new Person("firstName1", "lastName1","address2","city2","zip2","phone2","email2");
        //WHEN
        when(personService.findPersonByFirstNameAndLastName(requestBodyPerson.getFirstName(),requestBodyPerson.getLastName())).thenReturn(Optional.ofNullable(person));
        personService.updatePerson(requestBodyPerson);
        //THEN
        verify(personRepository,times(1)).updatePerson(requestBodyPerson);
    }

    @Test
    @DisplayName(" deletePerson calls personRepository.deleteByFirstNameAndLastName()")
    public void testDeletePerson(){
        //WHEN

        personService.deletePerson(person.getFirstName(), person.getLastName());
        //THEN
        verify(personRepository,times(1)).deleteByFirstNameAndLastName(person.getFirstName(),person.getLastName());
    }

    @Test
    @DisplayName("getAllPerson calls personRepository.getAllPerson()")
    public void getAllPersonTest() throws IOException {
        //WHEN

        personService.getAllPerson();
        //THEN
        verify(personRepository,times(1)).getAllPerson();
    }

}