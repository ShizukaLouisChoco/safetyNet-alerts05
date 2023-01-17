package com.safetynet.alerts.repository.impl;


import com.safetynet.alerts.dao.DataStorage;
import com.safetynet.alerts.exception.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;


@Repository
public class MedicalRecordRepositoryImpl implements MedicalRecordRepository {

    private final static Logger logger = LogManager.getLogger("MedicalRecordRepository");


    private final DataStorage dataStorage;

    public MedicalRecordRepositoryImpl(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }
    /**
     * Get all medical records from data
     * @return list of all medical records in data
     */
    @Override
    public Stream<MedicalRecord> getAllMedicalRecords(){
        return dataStorage.getData().getMedicalrecords().stream();
    }

    /**
     * Get optional medicalRecord filtred by firstName and lastName
     * @param firstName of medical record to search
     * @param lastName of medical record to search
     * @return optional of medical record with first name and last name searched
     */
    @Override
    public Optional<MedicalRecord> findMedicalRecordByFirstNameAndLastName(final String firstName, final String lastName) {
        return getAllMedicalRecords()
                .filter(allMedicalRecords -> allMedicalRecords.getFirstName().equals(firstName) && allMedicalRecords.getLastName().equals(lastName))
                .findFirst();

    }

    /**
     * save a new medical record
     * @param medicalRecord to save
     * @return medicalrecord saved
     * @exception IllegalArgumentException when medical record to save already exists
     */
    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord)  {

        if(findMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(),medicalRecord.getLastName()).isPresent()){
            throw new IllegalArgumentException("Medical Record exists");
        }
        dataStorage.getMedicalRecords().add(medicalRecord);
        return medicalRecord;
    }
    /**
     * update existing medical record
     * @param firstName of medical record to search
     * @param lastName of medical record to search
     * @param medicalRecord to update
     * @exception MedicalRecord when searching medical record doesn't exist
     */
    @Override
    public void updateMedicalRecord(String firstName, String lastName,MedicalRecord medicalRecord){
        var existingMedicalRecord = findMedicalRecordByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new MedicalRecordNotFoundException());

        var index = dataStorage.getMedicalRecords().indexOf(existingMedicalRecord);
        dataStorage.getMedicalRecords().set(index, medicalRecord);
    }
    /**
     * delete existing medical record
     * @param firstName of medical record to delete
     * @param lastName of medical record to delete
     */
    @Override
    public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        findMedicalRecordByFirstNameAndLastName(firstName,lastName).ifPresent(m->removeMedicalRecord(m));
    }
    /**
     * remove medical record from data
     * @param medicalRecord to delete
     */
    private void removeMedicalRecord(MedicalRecord medicalRecord){
        dataStorage.getData().getMedicalrecords().remove(medicalRecord);
    }
}