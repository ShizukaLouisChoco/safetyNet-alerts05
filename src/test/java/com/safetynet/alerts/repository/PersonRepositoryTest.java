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
        testList.add( new Person("first name1","last name1","address1","city1","zip1","phone1","email1"));
        testList.add(new Person("first name2","last name2","address2","city2","zip2","phone2","email2"));
        testList.add(new Person("first name3","last name3","address3","city3","zip3","phone3","email3"));

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
                .filter(p -> p.getFirstName().equals("first name1") && p.getLastName().equals("last name1"))
                .findFirst()
                .orElseThrow(PersonNotFoundException::new));
        //WHEN
        Optional<Person> response = personRepository.findPersonByFirstNameAndLastName("first name1","last name1");
        //THEN
        assertThat(Optional.of(response)).hasValue(optionalPerson);
        assertThat(response.toString()).isEqualTo("Optional[Person(firstName=first name1, lastName=last name1, address=address1, city=city1, zip=zip1, phone=phone1, email=email1)]");
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
                .filter(p -> p.getCity().equals("city1"))
                .toList();
        //WHEN
        Stream<Person> response = personRepository.getAllPersonsByCity("city1");
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
        personRepository.deleteByFirstNameAndLastName("first name1", "last name1");
        //THEN
        var dataAfter = dataStorage.getPersons();

        assertThat(dataBefore).isNotSameAs(dataAfter);
        assertThat(dataBefore.toString()).isEqualTo("[Person(firstName=first name1, lastName=last name1, address=address1, city=city1, zip=zip1, phone=phone1, email=email1), Person(firstName=first name2, lastName=last name2, address=address2, city=city2, zip=zip2, phone=phone2, email=email2), Person(firstName=first name3, lastName=last name3, address=address3, city=city3, zip=zip3, phone=phone3, email=email3)]");
        assertThat(dataAfter.toString()).isEqualTo("[Person(firstName=first name2, lastName=last name2, address=address2, city=city2, zip=zip2, phone=phone2, email=email2), Person(firstName=first name3, lastName=last name3, address=address3, city=city3, zip=zip3, phone=phone3, email=email3)]");
    }

    @Test
    @DisplayName("savePerson(Person person) save person and returns person")
    public void testSavePerson(){
        //GIVEN
        Person person = new Person("first name4","last name4","address4","city4","zip4","phone4","email4");
        var originalData = dataStorage.getPersons();
        var dataBefore = new ArrayList<>(originalData);
        assertThat(dataBefore).doesNotContain(person);
        //WHEN
        personRepository.savePerson(person);
        //THEN
        var dataAfter = dataStorage.getPersons();

        assertThat(dataBefore).isNotSameAs(dataAfter);
        assertThat(dataAfter).contains(person);
        assertThat(dataBefore.toString()).isEqualTo("[Person(firstName=first name1, lastName=last name1, address=address1, city=city1, zip=zip1, phone=phone1, email=email1), Person(firstName=first name2, lastName=last name2, address=address2, city=city2, zip=zip2, phone=phone2, email=email2), Person(firstName=first name3, lastName=last name3, address=address3, city=city3, zip=zip3, phone=phone3, email=email3)]");
        assertThat(dataAfter.toString()).isEqualTo("[Person(firstName=first name1, lastName=last name1, address=address1, city=city1, zip=zip1, phone=phone1, email=email1), Person(firstName=first name2, lastName=last name2, address=address2, city=city2, zip=zip2, phone=phone2, email=email2), Person(firstName=first name3, lastName=last name3, address=address3, city=city3, zip=zip3, phone=phone3, email=email3), Person(firstName=first name4, lastName=last name4, address=address4, city=city4, zip=zip4, phone=phone4, email=email4)]");
    }

    @Test
    @DisplayName("updatePerson() updates person")
    public void testUpdatePerson(){
        //GIVEN
        Person person = new Person("first name1","last name1","address01","city01","zip01","phone01","email01");
        var originalData = dataStorage.getPersons();
        var dataBefore = new ArrayList<>(originalData);
        assertThat(dataBefore).doesNotContain(person);
        //WHEN
        personRepository.updatePerson(person);
        //THEN
        var dataAfter = dataStorage.getPersons();

        assertThat(dataBefore).isNotSameAs(dataAfter);
        assertThat(dataAfter).contains(person);
        assertThat(dataBefore.toString()).isEqualTo("[Person(firstName=first name1, lastName=last name1, address=address1, city=city1, zip=zip1, phone=phone1, email=email1), Person(firstName=first name2, lastName=last name2, address=address2, city=city2, zip=zip2, phone=phone2, email=email2), Person(firstName=first name3, lastName=last name3, address=address3, city=city3, zip=zip3, phone=phone3, email=email3)]");
        assertThat(dataAfter.toString()).isEqualTo("[Person(firstName=first name1, lastName=last name1, address=address01, city=city01, zip=zip01, phone=phone01, email=email01), Person(firstName=first name2, lastName=last name2, address=address2, city=city2, zip=zip2, phone=phone2, email=email2), Person(firstName=first name3, lastName=last name3, address=address3, city=city3, zip=zip3, phone=phone3, email=email3)]");
    }

}