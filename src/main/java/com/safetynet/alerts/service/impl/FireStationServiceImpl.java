package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FireStationServiceImpl implements FireStationService {

    private final static Logger logger = LogManager.getLogger("FireStationServiceImpl");
    private final FireStationRepository fireStationRepository;


    public FireStationServiceImpl(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }
    /**
     * Get optional fireStation by address
     * @param address to search
     * @return optional fireStation with address searched
     */
    @Override
    public Optional<FireStation> findFireStationByAddress(final String address)  {
        logger.info(".findFireStationByAddress");
        logger.debug(" details: address:{}", address);

        return fireStationRepository.findStationNumberByAddress(address);
    }
    /**
     * Get a list of all fireStation
     * @return list of all fire station in data
     */
    @Override
    public List<FireStation> getAllFireStations()  {
        logger.info(".getAllFireStations");

        return fireStationRepository.getAllFireStation().toList();
    }

    /**
     * Save a new fireStation
     * @param fireStation to save
     * @return fireStation saved
     */
    @Override
    public FireStation saveFireStation(FireStation fireStation){
        logger.info(".saveFireStation");
        logger.debug(" details: fireStation:{}", fireStation);

        return fireStationRepository.saveFireStation(fireStation);
    }

    /**
     * Update existing fireStation
     * @param address of exisiting fireStation
     * @param fireStation to update
     * @return updated fireStation
     */
    @Override
    public FireStation updateFireStation(String address, FireStation fireStation) throws FireStationNotFoundException {
        logger.info(".updateFireStation");

        final var currentFireStation = findFireStationByAddress(address)
                .orElseThrow(() -> new FireStationNotFoundException());

        final FireStation updatedFireStation = currentFireStation.update(fireStation);
        fireStationRepository.updateFireStation(updatedFireStation);
        return updatedFireStation;

    }
    /**
     * Delete existing fireStation
     * @param address of fireStation to delete
     */
    @Override
    public void deleteFireStation(String address){
        logger.info(".deleteFireStation");
        logger.debug(" details: address:{}", address);

        fireStationRepository.deleteFireStationByAddress(address);
    }

}
