package com.safetynet.alerts.service;


import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.model.FireStation;

import java.util.List;
import java.util.Optional;

public interface FireStationService {
    FireStation saveFireStation(FireStation fireStation) ;

    FireStation updateFireStation(String address, FireStation fireStation) throws FireStationNotFoundException;

    void deleteFireStation(String address) ;

    Optional<FireStation> findFireStationByAddress(String address) ;

    List<FireStation> getAllFireStations() ;
}
