package com.safetynet.alerts.controller;


import com.safetynet.alerts.dto.ChildInfoWithFamilyDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonInfosWithAdultsAndChildrenNumberDTO;
import com.safetynet.alerts.dto.PersonInfosWithFireStationNumberDTO;
import com.safetynet.alerts.service.EndpointService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class EndpointController {
    private final static Logger logger = LogManager.getLogger("EndpointController");

    private final EndpointService endpointService;

    public EndpointController(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    /**
     * GetMapping - get a set of person information & number of adult and children who has same fire station number requested
     * url : http://localhost:8080/firestation?stationNumber={stationNumber}
     * @param stationNumber to search
     * @return The set of person information
     */
    @GetMapping(value = "firestation")
    public PersonInfosWithAdultsAndChildrenNumberDTO getPersonInfosWithAdultAndChildrenNumber(@RequestParam("stationNumber") final Integer stationNumber) {
        logger.info(".getPersonInfosWithAdultAndChildrenNumber");
        logger.info("Accessed endpoint URL:/firestation");
        logger.debug("Request details: GETMapping, stationNumber :{}", stationNumber);
        return endpointService.getPersonInfosWithAdultAndChildrenNumberByFireStationNumber(stationNumber);
    }

    /**
     * GetMapping - get a set of children who live in the address requested
     * url : http://localhost:8080/childAlert?address={address}
     * if there's no child, return String empty
     * @param address to search
     * @return The set of children's information with family list
     */

    @GetMapping(value = "childAlert")
    public  Set<ChildInfoWithFamilyDTO> getChildrenInfosWithFamilyByAddress(@RequestParam("address")final String address){
        logger.info(".getChildrenInfosWithFamilyByAddress");
        logger.info("Accessed endpoint URL:/childAlert");
        logger.debug("Request details: GETMapping, address :{}", address);
        return  endpointService.getChildrenInfosWithFamilyByAddress(address);
    }

    /**
     * GetMapping - get a set of phone number registered by fire station number requested
     * url : http://localhost:8080/phoneAlert?firestation={firestationNumber}
     * @param firestationNumber to search
     * @return The set of phone number
     */

    @GetMapping(value = "phoneAlert")
    public  Set<String> getPhoneNumberSetByFireStationNumber(@RequestParam("firestation")final Integer firestationNumber){
        logger.info(".getPhoneNumberSetByFireStationNumber");
        logger.info("Accessed endpoint URL:/phoneAlert");
        logger.debug("Request details: GETMapping, firestation :{}", firestationNumber);
        return endpointService.getPhoneNumbersByFireStationNumber(firestationNumber);
    }


    /**
     * GetMapping - get a person information list and station number by address
     * url : http://localhost:8080/fire?address={address}
     * @param address to search
     * @return The set of person info
     */

    @GetMapping(value = "fire")
    public PersonInfosWithFireStationNumberDTO getPersonInfosByAddress(@RequestParam("address") final String address) {
        logger.info(".getPersonInfosByAddress");
        logger.info("Accessed endpoint URL:/fire");
        logger.debug("Request details: GETMapping, address :{}", address);
        return endpointService.getPersonInfosWithFireStationNumber(address);
    }

    /**
     * GetMapping - get a set of person info ordered by fire station number by a set of fire station number requested
     * url : http://localhost:8080/flood/stations?stations={stationNumbers}
     * @param stationNumbers of Set
     * @return The Map : key = fire station number, value = person info
     */

    @GetMapping(value = "flood/stations")
    public Map<String, List<PersonInfoDTO>> getOrderedPersonInfoByStationNumbers(@RequestParam("stations")final Set<Integer> stationNumbers){
        logger.info(".getOrderedPersonInfoByStationNumbers");
        logger.info("Accessed endpoint URL:/flood/stations");
        logger.debug("Request details: GETMapping, stationNumbers :{}", stationNumbers);
        return endpointService.getPersonsOrderedByAddressByStationNumbers(stationNumbers);
    }


    /**
     * GetMapping - get a set of person info by first name or last name
     * url : http://localhost:8080/personInfo?firstName={firstname}
     * url : http://localhost:8080/personInfo?firstName={firstname}&lastName={lastName}
     * @param firstName to search which is required true
     * @param lastName to search which is required false
     * @return The set of person info
     */

    @GetMapping(value = "personInfo")
    public Set<PersonInfoDTO> getPersonsByFirstNameAndLastName(@RequestParam(name = "firstName", required = true)final String firstName,@RequestParam(name = "lastName", required = false)final String lastName){
        logger.info(".getPersonsByFirstNameAndLastName");
        logger.info("Accessed endpoint URL:/personInfo");
        logger.debug("Request details: GETMapping, firstName :{}, lastName {}", firstName,lastName);
        return endpointService.getPersonInfosByFirstNameOrLastName(firstName,lastName);
    }

    /**
     * GetMapping - get a set of email by city
     * url : http://localhost:8080/communityEmail?city={city}
     * @param city to search
     * @return The set of email
     */
    @GetMapping(value="communityEmail")
    public Set<String> getEmailSetByCity(@RequestParam("city")final String city)  {
        logger.info(".getEmailSetByCity");
        logger.info("Accessed endpoint URL:/communityEmail");
        logger.debug("Request details: GETMapping, city :{}", city);
        return endpointService.getEmailsByCity(city);
    }
}