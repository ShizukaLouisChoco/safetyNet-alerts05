package com.safetynet.alerts.service.impl;

import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.FireStationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class FireStationServiceImpl implements FireStationService {

    //log.info("FireStationServiceImpl");
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
        return fireStationRepository.findStationNumberByAddress(address);
    }
    /**
     * Get a list of all fireStation
     * @return list of all fire station in data
     */
    @Override
    public List<FireStation> getAllFireStations()  {
        return fireStationRepository.getAllFireStation().toList();
    }

    /**
     * Save a new fireStation
     * @param fireStation to save
     * @return fireStation saved
     */
    @Override
    public FireStation saveFireStation(FireStation fireStation){
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
        fireStationRepository.deleteFireStationByAddress(address);
    }

}
