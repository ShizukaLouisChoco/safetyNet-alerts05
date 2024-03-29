package com.safetynet.alerts.service.impl;


import com.safetynet.alerts.dto.ChildInfoWithFamilyDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonInfosWithAdultsAndChildrenNumberDTO;
import com.safetynet.alerts.dto.PersonInfosWithFireStationNumberDTO;
import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.exception.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.EndpointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EndpointServiceImpl implements EndpointService {
    private final static Logger logger = LogManager.getLogger("EndpointServiceImpl");


    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public EndpointServiceImpl(FireStationRepository fireStationRepository,
                               PersonRepository personRepository,
                               MedicalRecordRepository medicalRecordRepository){
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    /**
     * get person infos with adult and child number by fire station number
     * url : http://localhost:8080/firestation?stationNumber={fireStationNumber}
     * @param fireStationNumber to search
     * @return PersonInfosWithAdultsAndChildrenNumberDTO
     */
    @Override
    public PersonInfosWithAdultsAndChildrenNumberDTO getPersonInfosWithAdultAndChildrenNumberByFireStationNumber(Integer fireStationNumber) {
        logger.info(".getPersonInfosWithAdultAndChildrenNumberByFireStationNumber");
        logger.debug(" details: fireStationNumber:{}", fireStationNumber);

        Set<String> fireStationAddress = fireStationRepository
                .getFireStationsByStationNumber(fireStationNumber)
                .map(f->f.getAddress())
                .collect(Collectors.toSet());

        var person = personRepository
                .getPersonByAddressList(fireStationAddress)
                .map(p -> new PersonInfoDTO(p, findMedicalRecordByPerson(p)))
                .collect(Collectors.toSet());

        return  new PersonInfosWithAdultsAndChildrenNumberDTO(person);

    }

    /**
     * get the list of children's information with family member gathered by address
     * url : http://localhost:8080/childAlert?address=<address>
     * @param address to search
     * @return set of ChildInfoWirhFamilyDTO, if there's no child, return String empty
     */
    @Override
    public Set<ChildInfoWithFamilyDTO> getChildrenInfosWithFamilyByAddress(String address) {
        logger.info(".getChildrenInfosWithFamilyByAddress");
        logger.debug(" details: address: {}", address);

        var personList = personRepository
                .getAllByAddress(address)
                .map(p -> new PersonInfoDTO(p, findMedicalRecordByPerson(p)))
                .collect(Collectors.toSet());

        Set<ChildInfoWithFamilyDTO> children = personList
                .stream()
                .filter(p -> p.isMinor())
                .map(c -> new ChildInfoWithFamilyDTO(c, personList.stream().filter(p -> !p.equals(c)).collect(Collectors.toSet())))
                .collect(Collectors.toSet());
        return children;
    }

    /**
     * get phoneNumber set by fire station number
     * url : http://localhost:8080/phoneAlert?firestation=<fireStationNumber>
     * @param fireStationNumber to search
     * @return The list of phone number
     */
    @Override
    public Set<String> getPhoneNumbersByFireStationNumber(Integer fireStationNumber) {
        logger.info(".getPhoneNumbersByFireStationNumber");
        logger.debug(" details: fireStationNumber: {}", fireStationNumber);

        var fireStationAddress = fireStationRepository
                .getFireStationsByStationNumber(fireStationNumber)
                .map(f->f.getAddress())
                .collect(Collectors.toSet());

        return personRepository
                .getPersonByAddressList(fireStationAddress)
                .map(p -> p.getPhone())
                .collect(Collectors.toSet());
    }

    /**
     * Get person info ordered by address filtered by station number set
     * url : http://localhost:8080/flood/stations?stations=<fireStationNumbers>
     * @param fireStationNumbers to search
     * @return Map : key = address, value = personInfoDTO list
     */
    @Override
    public Map<String, List<PersonInfoDTO>> getPersonsOrderedByAddressByStationNumbers(Set<Integer> fireStationNumbers) {
        logger.info(".getPersonsOrderedByAddressByStationNumbers");
        logger.debug(" details: fireStationNumbers:{}", fireStationNumbers);

        //get Address list from station numbers list
        Set<String> fireStationAddressList = fireStationRepository
                .getAllFireStationByStationNumberList(fireStationNumbers)
                .map(f -> f.getAddress())
                .collect(Collectors.toSet());

        //get person from fireStationAddressList
        return personRepository
                .getPersonByAddressList(fireStationAddressList)
                .map( p -> new PersonInfoDTO(p, findMedicalRecordByPerson(p)))
                .collect(Collectors.groupingBy(PersonInfoDTO::getAddress));
    }


    /**
     * get person info by firstName or lastName
     * url : http://localhost:8080/personInfo?firstName={firstName}&lastName={lastName}
     * @return The set of personInfoDTO
     */
    @Override
    public Set<PersonInfoDTO> getPersonInfosByFirstNameOrLastName(String firstName, String lastName) {
        logger.info(".getPersonInfosByFirstNameOrLastName");
        logger.debug(" details: firstName:{},lastName: {}", firstName, lastName);

        return personRepository.findPersonsByFirstNameOrLastName(firstName, lastName)
                .map(p -> new PersonInfoDTO(p, findMedicalRecordByPerson(p)))
                .collect(Collectors.toSet());
    }

    /**
     * Get email set filtered by city
     * url : http://localhost:8080/communityEmail?city={city}
     * @param city to search
     * @return The set of email with city searched
     */
    @Override
    public Set<String> getEmailsByCity(String city) {
        logger.info(".getEmailsByCity");
        logger.debug(" details: city: {}", city);

        //get Person list by City
        return personRepository
                .getAllPersonsByCity(city)
                .map(Person::getEmail)
                .collect(Collectors.toSet());
    }
    /**
     * Get person infos with fire station number by address
     * @param address to search
     * @return PersonInfosWithFireStationNumberDTO with address searched
     * @exception FireStationNotFoundException when firestation with address searched doesn't exist
     */
    @Override
    public PersonInfosWithFireStationNumberDTO getPersonInfosWithFireStationNumber(String address){
        logger.info(".getPersonInfosWithFireStationNumber");
        logger.debug(" details: address:{}", address);

        // number de la firestation
        // trouver les personnes a cette adresse
        // trouver les infos medicals pour ces personnes

        var stationNumber = fireStationRepository.findStationNumberByAddress(address)
                .orElseThrow(FireStationNotFoundException::new)
                .getStation();

        var personInfoWithMedicalRecordsDTOs = personRepository.getAllByAddress(address)
                .map( p -> new PersonInfoDTO(p, findMedicalRecordByPerson(p)))
                .collect(Collectors.toSet());

        return new PersonInfosWithFireStationNumberDTO(personInfoWithMedicalRecordsDTOs, stationNumber);

    }

    /**
     * Get medical records by person
     * @param person to search
     * @return medicalRecord with person searched
     * @exception MedicalRecordNotFoundException when the medical record with first name and last name of person searched doesn't exist
     */
    private MedicalRecord findMedicalRecordByPerson(Person person){
        logger.info(".findMedicalRecordByPerson");
        logger.debug(" details: Person {}", person);

        return medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName())
                .orElseThrow(MedicalRecordNotFoundException::new);
    }
}