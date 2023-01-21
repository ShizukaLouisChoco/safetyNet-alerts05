package com.safetynet.alerts.dao.impl;


import com.safetynet.alerts.dao.DataStorage;
import com.safetynet.alerts.model.AllData;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Log4j2
@NoArgsConstructor
public class DataStorageImpl implements DataStorage {

    private final static Logger logger = LogManager.getLogger("DataStorageImpl");

    @Getter
    @Setter
    private AllData data;

    /**
     * Get all persons from data
     * @return list of all person in data
     */
    @Override
    public List<Person> getPersons() {
        log.info(".getPersons");

        return data.getPersons();
    }

    /**
     * Get all fire station from data
     * @return list of all fire station in data
     */
    @Override
    public List<FireStation> getFireStations() {
        log.info(".getFireStations");

        return data.getFirestations();
    }
    /**
     * Get all medical record from data
     * @return list of all medical record in data
     */
    @Override
    public List<MedicalRecord> getMedicalRecords() {
        log.info(".getMedicalRecords");

        return data.getMedicalrecords();
    }

    /**
     * Get all data
     * @param data to set
     */
    @Override
    public void setAllData(AllData data) {
        log.info(".setAllData {}",data);

        this.data = data;
    }

}
