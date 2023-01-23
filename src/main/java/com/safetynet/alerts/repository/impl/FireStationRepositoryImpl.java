package com.safetynet.alerts.repository.impl;

import com.safetynet.alerts.dao.DataStorage;
import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public class FireStationRepositoryImpl implements FireStationRepository {

    private final static Logger logger = LogManager.getLogger("FireStationRepositoryImpl");

    private final DataStorage dataStorage;

    public FireStationRepositoryImpl(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Get all fire station stream
     * @return stream all fireStation
     */
    @Override
    public Stream<FireStation> getAllFireStation() {
        logger.info(".getAllFireStation");
        return dataStorage.getData().getFirestations().stream();
    }


    /**
     * Get firestation stream filtered by number
     * @param number of fire station number
     * @return stream firestation with station number searched
     */
    @Override
    public Stream<FireStation> getFireStationsByStationNumber(Integer number) {
        logger.info(".getFireStationsByStationNumber");
        logger.debug(" details: StationNumber:{}", number);

        return dataStorage
                .getFireStations()
                .stream()
                .filter(f -> f.getStation().equals(number));
    }
    /**
     * Get stream firestation filtered by address
     * @param address of firestation
     * @return stream of firestation with address searched
     */
    @Override
    public Stream<FireStation> getFireStationsByAddress(String address) {
        logger.info(".getFireStationsByAddress");
        logger.debug(" details: address:{}", address);

        return dataStorage
                .getFireStations()
                .stream()
                .filter(f -> f.getAddress().equals(address));
    }
    /**
     * Get stream firestation filtered by station number list
     * @param fireStationNumbers of set
     * @return stream of firestation with fire station numbers searched
     */
    @Override
    public Stream<FireStation> getAllFireStationByStationNumberList(Set<Integer> fireStationNumbers) {
        logger.info(".getAllFireStationByStationNumberList");
        logger.debug(" details: fireStationNumbers: {}", fireStationNumbers);

        return dataStorage
                .getFireStations()
                .stream()
                .filter(f -> fireStationNumbers.contains(f.getStation()));
    }

    /**
     * Get Optional firestation filtered by address
     * @param address of firestation
     * @return optional of firestation with address searched
     */
    @Override
    public Optional<FireStation> findStationNumberByAddress(String address) {
        logger.info(".findStationNumberByAddress");
        logger.debug(" details: address:{}", address);

        return getAllFireStation()
                .filter(allFireStations -> allFireStations.getAddress().equals(address)) // return ONLY firstation with getAddress = address => Stream <FireStation> with getAddress = address
                .findFirst();
    }
    /**
     * delete firestation with address serached
     * @param address of firestation to delete
     */
    @Override
    public void deleteFireStationByAddress(String address) {
        logger.info(".deleteFireStationByAddress");
        logger.debug(" details: address:{}", address);

        findStationNumberByAddress(address).ifPresent(this::removeFireStation);
    }
    /**
     * save a new firestation
     * @param fireStation to save
     * @return firestation saved
     * @exception IllegalArgumentException when address of firestation is already exists
     */
    @Override
    public FireStation saveFireStation(FireStation fireStation)  {
        logger.info(".saveFireStation");
        logger.debug(" details: fireStation:{}", fireStation);

        if (findStationNumberByAddress(fireStation.getAddress()).isPresent()) {
            throw new IllegalArgumentException("FireStation already exists");
        }

        dataStorage.getFireStations().add(fireStation);
        return fireStation;
    }

    /**
     * update existing firestation
     * @param fireStation to update
     * @exception FireStationNotFoundException when the address of firestation to update doesn't exist
     */
    @Override
    public void updateFireStation(FireStation fireStation) throws FireStationNotFoundException  {
        logger.info(".updateFireStation");
        logger.debug(" details: fireStation: {}", fireStation);

        var existingStation = findStationNumberByAddress(fireStation.getAddress())
                .orElseThrow(() -> new FireStationNotFoundException());

        var index = dataStorage.getFireStations().indexOf(existingStation);
        dataStorage.getFireStations().set(index,fireStation);
    }

    /**
     * remove firestation from data
     * @param fireStation to remove
     */
    //remove method
    private void removeFireStation(FireStation fireStation){
        logger.info(".removeFireStation");
        logger.debug(" details: fireStation: {}", fireStation);

        dataStorage.getData().getFirestations().remove(fireStation);

    }

}