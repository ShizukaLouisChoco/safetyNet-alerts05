package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    //log.info("PersonServiceImpl");

    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> findPersonByFirstNameAndLastName(final String firstName, final String lastName) {
        return personRepository.findPersonByFirstNameAndLastName(firstName,lastName);
    }


    @Override
    public Person savePerson(Person person) {
        return personRepository.savePerson(person);
    }

    @Override
    public Person updatePerson(Person person) throws PersonNotFoundException {
        //String msg = "No person with that name exists";
        final var currentPerson = findPersonByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .orElseThrow(() -> new PersonNotFoundException());

        final var updatedPerson = currentPerson.update(person);

        personRepository.updatePerson(updatedPerson);
        return updatedPerson;
    }

    @Override
    public void deletePerson(String firstName, String lastName) {
        personRepository.deleteByFirstNameAndLastName(firstName,lastName);

    }

    @Override
    public Stream<Person> getAllPerson() {
        return personRepository.getAllPerson();
    }
}
