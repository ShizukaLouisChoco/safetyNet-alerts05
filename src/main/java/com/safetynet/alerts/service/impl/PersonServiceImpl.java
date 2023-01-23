package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PersonServiceImpl implements PersonService {

    private final static Logger logger = LogManager.getLogger("PersonServiceImpl");

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findPersonByFirstNameAndLastName(final String firstName, final String lastName) {
        logger.info(".findPersonByFirstNameAndLastName");
        logger.debug(" details: firstName:{}, lastName:{}", firstName, lastName);

        return personRepository.findPersonByFirstNameAndLastName(firstName,lastName);
    }


    @Override
    public Person savePerson(Person person) {
        logger.info(".savePerson");
        logger.debug(" details: Person :{}", person);

        return personRepository.savePerson(person);
    }

    @Override
    public Person updatePerson(Person person) throws PersonNotFoundException {
        logger.info(".updatePerson");
        logger.debug(" details: Person :{}", person);

        final var currentPerson = findPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .orElseThrow(() -> new PersonNotFoundException());

        final var updatedPerson = currentPerson.update(person);

        personRepository.updatePerson(updatedPerson);
        return updatedPerson;
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        logger.info(".deletePerson");
        logger.debug(" details: firstName:{},lastName: {}", firstName,lastName);

        personRepository.deleteByFirstNameAndLastName(firstName,lastName);

    }

    @Override
    public Stream<Person> getAllPerson() {
        logger.info(".getAllPerson");

        return personRepository.getAllPerson();
    }
}
