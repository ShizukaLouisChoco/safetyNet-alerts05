package com.safetynet.alerts.repository;


import com.safetynet.alerts.dao.impl.DataStorageImpl;
import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.AllData;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.impl.PersonRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatStream;

@ExtendWith(SpringExtension.class)
public class PersonRepositoryTest {

    private final DataStorageImpl dataStorage = new DataStorageImpl();
    private final PersonRepositoryImpl personRepository = new PersonRepositoryImpl(dataStorage);

    private List<Person> testList;
    @BeforeEach
    public void init(){
        testList = new ArrayList<>();
        testList.add( new Person("adult1","adult1","address1","Culver","97451","841-874-6512","jaboyd@email.com"));
        testList.add(new Person("child1","child1","address1","Culver","97451","841-874-6512","jaboyd@email.com"));
        testList.add(new Person("adult2","adult2","address2","Culver","97451","841-874-6512","soph@email.com"));

        AllData allData = new AllData(testList, null, null);
        this.dataStorage.setData(allData);

    }


    @Test
    @DisplayName("getAllPersons() returns all person List<Person>")
    public void testGetAllPerson(){
        //GIVEN
        //WHEN
        Stream<Person> response = personRepository.getAllPerson();
        //THEN
        assertThat(response).containsAnyElementsOf(testList);
    }

    @Test
    @DisplayName("findPersonByFirstNameAndLastName returns Optional<Person> filtered by firstName and lastName") public void testFindPersonByFirstNameAndLastName(){
        //GIVEN
        Optional<Person> optionalPerson = Optional.ofNullable(testList.stream()
                .filter(p -> p.getFirstName().equals("adult1") && p.getLastName().equals("adult1"))
                .findFirst()
                .orElseThrow(PersonNotFoundException::new));
        //WHEN
        Optional<Person> response = personRepository.findPersonByFirstNameAndLastName("adult1","adult1");
        //THEN
        assertThat(Optional.of(response)).hasValue(optionalPerson);
        assertThat(response.toString()).isEqualTo("Optional[Person(firstName=adult1, lastName=adult1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com)]");
    }


    @Test
    @DisplayName("getAllByAddress(String address) returns Stream<Person> filtered by address")
    public void testGetAllByAddress(){
        //GIVEN
        var streamPerson = testList
                .stream()
                .filter(p -> p.getAddress().equals("address1"))
                .toList();
        //WHEN
        Stream<Person> response = personRepository.getAllByAddress("address1");
        //THEN
        assertThatStream(response).isEqualTo(streamPerson);

    }
    @Test
    @DisplayName("getPersonByAddressList(List<String> fireStationAddressList)  returns Stream<Person> filtered by list of firestationAddressList")
    public void testGetPersonByAddressList(){
        //GIVEN
        Set<String> addressList = new HashSet<>();
        addressList.add("address1");
        addressList.add("address2");

        var streamPerson = testList
                .stream()
                .filter(p -> addressList.contains(p.getAddress()))
                .toList();
        //WHEN
        Stream<Person> response = personRepository.getPersonByAddressList(addressList);
        //THEN
        assertThatStream(response).isEqualTo(streamPerson);
    }

    @Test
    @DisplayName("getAllPersonsByCity(String city) returns Stream<Person> filtered by city")
    public void testGetPersonByCity(){
        //GIVEN
        var streamPerson = testList
                .stream()
                .filter(p -> p.getCity().equals("Culver"))
                .toList();
        //WHEN
        Stream<Person> response = personRepository.getAllPersonsByCity("Culver");
        //THEN
        assertThatStream(response).isEqualTo(streamPerson);
    }


    @Test
    @DisplayName("deleteByFirstNameAndLastName deletes person with firstName and lastName")
    public void testDeleteByFirstNameAndLastName(){
        //GIVEN
        var originalData  = dataStorage.getPersons();
        var dataBefore = new ArrayList<>(originalData);

        //WHEN
        personRepository.deleteByFirstNameAndLastName("adult1", "adult1");
        //THEN
        var dataAfter = dataStorage.getPersons();

        assertThat(dataBefore).isNotSameAs(dataAfter);
        assertThat(dataBefore.toString()).isEqualTo("[Person(firstName=adult1, lastName=adult1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=child1, lastName=child1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=adult2, lastName=adult2, address=address2, city=Culver, zip=97451, phone=841-874-6512, email=soph@email.com)]");
        assertThat(dataAfter.toString()).isEqualTo("[Person(firstName=child1, lastName=child1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=adult2, lastName=adult2, address=address2, city=Culver, zip=97451, phone=841-874-6512, email=soph@email.com)]");
    }

    @Test
    @DisplayName("savePerson(Person person) save person and returns person")
    public void testSavePerson(){
        //GIVEN
        Person person = new Person("adult4","adult4","address3","Culver","97451","841-874-7512","ward@email.com");
        var originalData = dataStorage.getPersons();
        var dataBefore = new ArrayList<>(originalData);
        assertThat(dataBefore).doesNotContain(person);
        //WHEN
        personRepository.savePerson(person);
        //THEN
        var dataAfter = dataStorage.getPersons();

        assertThat(dataBefore).isNotSameAs(dataAfter);
        assertThat(dataAfter).contains(person);
        assertThat(dataBefore.toString()).isEqualTo("[Person(firstName=adult1, lastName=adult1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=child1, lastName=child1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=adult2, lastName=adult2, address=address2, city=Culver, zip=97451, phone=841-874-6512, email=soph@email.com)]");
        assertThat(dataAfter.toString()).isEqualTo("[Person(firstName=adult1, lastName=adult1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=child1, lastName=child1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=adult2, lastName=adult2, address=address2, city=Culver, zip=97451, phone=841-874-6512, email=soph@email.com), Person(firstName=adult4, lastName=adult4, address=address3, city=Culver, zip=97451, phone=841-874-7512, email=ward@email.com)]");
    }

    @Test
    @DisplayName("updatePerson() updates person")
    public void testUpdatePerson(){
        //GIVEN
        Person person = new Person("adult1","adult1","address01","city01","zip01","phone01","email01");
        var originalData = dataStorage.getPersons();
        var dataBefore = new ArrayList<>(originalData);
        assertThat(dataBefore).doesNotContain(person);
        //WHEN
        personRepository.updatePerson(person);
        //THEN
        var dataAfter = dataStorage.getPersons();

        assertThat(dataBefore).isNotSameAs(dataAfter);
        assertThat(dataAfter).contains(person);
        assertThat(dataBefore.toString()).isEqualTo("[Person(firstName=adult1, lastName=adult1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=child1, lastName=child1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=adult2, lastName=adult2, address=address2, city=Culver, zip=97451, phone=841-874-6512, email=soph@email.com)]");
        assertThat(dataAfter.toString()).isEqualTo("[Person(firstName=adult1, lastName=adult1, address=address01, city=city01, zip=zip01, phone=phone01, email=email01), Person(firstName=child1, lastName=child1, address=address1, city=Culver, zip=97451, phone=841-874-6512, email=jaboyd@email.com), Person(firstName=adult2, lastName=adult2, address=address2, city=Culver, zip=97451, phone=841-874-6512, email=soph@email.com)]");
    }

}