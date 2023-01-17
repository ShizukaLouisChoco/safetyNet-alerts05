package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.FireStation;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;


public interface FireStationRepository {
    Optional<FireStation> findStationNumberByAddress(String address);

    void deleteFireStationByAddress(String address) ;

    void updateFireStation(FireStation fireStation) ;

    FireStation saveFireStation(FireStation fireStation);

    Stream<FireStation> getAllFireStation();

    Stream<FireStation> getFireStationsByStationNumber(Integer number);
    Stream<FireStation> getFireStationsByAddress(String address);

    Stream<FireStation> getAllFireStationByStationNumberList(Set<Integer> fireStationNumbers);

}
