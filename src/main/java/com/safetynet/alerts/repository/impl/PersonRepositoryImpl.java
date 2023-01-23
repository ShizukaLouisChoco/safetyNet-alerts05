package com.safetynet.alerts.repository.impl;


import com.safetynet.alerts.dao.DataStorage;
import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final static Logger logger = LogManager.getLogger("PersonRepositoryImpl");

    private final DataStorage dataStorage;

    public PersonRepositoryImpl(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }


    /**
     * get a list of All persons
     * @return a list of persons
     */
    //get all person list
    @Override
    public Stream<Person> getAllPerson() {
        logger.info(".getAllPerson");

        return dataStorage.getData().getPersons().stream();
    }

    /**
     * get Stream of persons, find by address
     * @param address of persons to search
     * @return stream of persons with address searched
     */
    @Override
    public Stream<Person> getAllByAddress(String address){
        logger.info(".getAllByAddress");
        logger.debug(" details: address: {}",address);

        return  dataStorage.getPersons()
                .stream()
                .filter(p -> p.getAddress().equals(address));
    }
    /**
     * get Stream of persons, find by a list of fire station address list
     * @param fireStationAddressList of persons to search
     * @return stream of persons with address list searched
     */
    @Override
    public Stream<Person> getPersonByAddressList(Set<String> fireStationAddressList) {
        logger.info(".getPersonByAddressList");
        logger.debug(" details: fireStationAddressList: {}", fireStationAddressList);

        return dataStorage
                .getPersons()
                .stream()
                .filter(p ->  fireStationAddressList.contains(p.getAddress()));
    }
    /**
     * get Stream of persons, find by city
     * @param city of persons to search
     * @return stream of persons with city searched
     */
    @Override
    public Stream<Person> getAllPersonsByCity(String city) {
        logger.info(".getAllPersonsByCity");
        logger.debug(" details: city:{}", city);

        return dataStorage
                .getPersons()
                .stream()
                .filter(f -> f.getCity().equals(city));
    }

    /**
     * get Optional of persons, find by firstName and lastName
     * @param firstName of person to search
     * @param lastName of person to search
     * @return Optional of persons with first name and last name searched
     */
    @Override
    public Optional<Person> findPersonByFirstNameAndLastName(String firstName, String lastName) {
        logger.info(".findPersonByFirstNameAndLastName");
        logger.debug(" details: firstName:{},lastName: {}", firstName, lastName);

        return getAllPerson()
                .filter(allPersons -> allPersons.getFirstName().equals(firstName) && allPersons.getLastName().equals(lastName))
                .findFirst();
    }

    /**
     * return a list of persons, find by firstname or lastname
     * @param firstName of person to search
     * @param lastName of person to search
     * @return a list of persons with first name or last name searched
     */
    @Override
    public Stream<Person> findPersonsByFirstNameOrLastName(String firstName, String lastName) {
        logger.info(".findPersonsByFirstNameOrLastName");
        logger.debug(" details: firstName:{},lastName: {}", firstName, lastName);

        return getAllPerson()
                .filter(allPersons -> allPersons.getFirstName().equals(firstName) || allPersons.getLastName().equals(lastName));
    }

    /**
     * delete person, find by firstName and lastName
     * @param firstName of person to delete
     * @param lastName of person to delete
     */
    @Override
    public void deleteByFirstNameAndLastName(String firstName, String lastName)  {
        logger.info(".deleteByFirstNameAndLastName");
        logger.debug(" details: firstName:{},lastName: {}", firstName, lastName);

        findPersonByFirstNameAndLastName(firstName,lastName).ifPresent(p->removePerson(p));
    }
    /**
     * remove person from data
     * @param person to remove
     */
    private void removePerson(Person person) {
        logger.info(".removePerson");
        logger.debug(" details: Person:{}", person);

        dataStorage.getData().getPersons().remove(person);
    }

    /**
     * save a new person
     * @param person to save
     * @return person saved
     */
    @Override
    public Person savePerson(Person person) {
        logger.info(".savePerson");
        logger.debug(" details: Person:{}", person);

        if (findPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName()).isPresent()) {
            throw new IllegalArgumentException("Person exists");
        }
        dataStorage.getPersons().add(person);
        return person;
    }

    /**
     * update existing person
     * @param person to update
     * @exception PersonNotFoundException when first name and last name searched doesn't exist
     */
    //update method with update data.json
    @Override
    public void updatePerson(Person person) throws PersonNotFoundException {
        logger.info(".updatePerson");
        logger.debug(" details: Person:{}", person);

        var existingPerson = findPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .orElseThrow(()-> new PersonNotFoundException());
        var index = dataStorage.getPersons().indexOf(existingPerson);
        dataStorage.getPersons().set(index, person);
    }

}