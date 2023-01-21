package com.safetynet.alerts.controller;


import com.safetynet.alerts.exception.PersonNotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PersonController {
    private final static Logger logger = LogManager.getLogger("PersonController");
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * GetMapping - Get all person
     * url : http://localhost:8080/persons
     * @return list of all person
     */
    @GetMapping("persons")
    public List<Person> getAllPersons(){
        logger.info(".getAllPersons");

        return personService.getAllPerson().toList();
    }

    /**
     * PostMapping - add a new person
     * url : http://localhost:8080/person
     * @param person to add
     * @return The saved person object
     */
    @PostMapping(value = "person")
    public Person createPerson(@RequestBody Person person){
        logger.info(".createPerson Person : {} ",person);

        return personService.savePerson(person);
    }

    /**
     * PutMapping - Update an existing person
     * url : http://localhost:8080/person
     * @return The updated person updated
     */
    @PutMapping("person")
    public Person updatePerson(@RequestBody Person person) throws PersonNotFoundException {
        logger.info(".updatePerson person : {}",person);

        return personService.updatePerson(person);
    }

    /**
     * DeleteMapping - Delete a existing person
     * url : http://localhost:8080/person?firstName={firstName}&lastName{lastName}
     * @param firstName of the person to delete
     * @param lastName of the person to delete
     */
    @DeleteMapping("person")
    public void deletePerson(@RequestParam("firstName") final String firstName, @RequestParam("lastName") final String lastName) {
        logger.info(".deletePerson");

        personService.deletePerson(firstName, lastName);
    }

}