package com.safetynet.alerts.controller;


import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FireStationController {
    private final static Logger logger = LogManager.getLogger("FireStationController");
    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * GetMapping - Get all fire station
     * url : http://localhost:8080/firestations
     * @return List of fireStation
     */
    @GetMapping("firestations")
    public List<FireStation> getAllFireStations() {
        logger.info(".getAllFireStations");
        logger.info("Accessed endpoint URL:/firestations");
        logger.debug("Request details: GETMapping");

        return fireStationService.getAllFireStations();
    }

    /**
     * PostMapping - add a fire station
     * url : http://localhost:8080/firestation
     * @param fireStation to add
     * @return The saved fireStation object
     */
    @PostMapping(value = "firestation")
    public FireStation createFireStation(@RequestBody FireStation fireStation){
        logger.info(".createFireStation");
        logger.info("Accessed endpoint URL:/firestation");
        logger.debug("Request details: POSTMapping, Body fireStation :{}", fireStation);

        return fireStationService.saveFireStation(fireStation);
    }

    /**
     * PutMapping - Update an existing fire station
     * url : http://localhost:8080/firestation
     * @return The updated fire station updated
     */
    @PutMapping("firestation")
    public FireStation updateFireStation(@RequestBody FireStation fireStation) throws FireStationNotFoundException{
        logger.info(".updateFireStation");
        logger.info("Accessed endpoint URL:/firestation");
        logger.debug("Request details: PUTMapping, Body fireStation :{}", fireStation);

        return fireStationService.updateFireStation(fireStation.getAddress(), fireStation);
    }

    /**
     * DeleteMapping - Delete a fire station
     * url : http://localhost:8080/firestation?address={address}
     * @param address of the fire station to delete
     */
    @DeleteMapping("firestation")
    public void deleteFireStation(@RequestParam("address") final String address){
        logger.info(".deleteFireStation");
        logger.info("Accessed endpoint URL:/firestation");
        logger.debug("Request details: DELETEMapping, address: {}", address);

        fireStationService.deleteFireStation(address);
    }


}