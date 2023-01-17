package com.safetynet.alerts.dao;


import com.safetynet.alerts.model.AllData;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;

import java.util.List;


public interface DataStorage {
    AllData getData();

    List<Person> getPersons() ;

    List<FireStation> getFireStations() ;

    List<MedicalRecord> getMedicalRecords() ;

    void setAllData(AllData data);


}