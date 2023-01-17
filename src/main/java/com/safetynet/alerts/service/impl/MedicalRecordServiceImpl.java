package com.safetynet.alerts.service.impl;


import com.safetynet.alerts.exception.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.service.MedicalRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Log4j2
@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    //log.info("MedicalRecordServiceImpl");

    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
    }

    /**
     * Get all medicalRecord from data
     *
     * @return list of all medical record in data
     */
    @Override
    public Stream<MedicalRecord> getAllMedicalRecords(){
        return medicalRecordRepository.getAllMedicalRecords();
    }

    /**
     * Save a new medicalRecord
     * @param medicalRecord to save
     * @return saved medicalRecord
     */
    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.saveMedicalRecord(medicalRecord);
    }
    /**
     * Update existing medicalRecord
     * @param firstName of medical record to update
     * @param lastName of medical record to update
     * @param medicalRecord to update
     * @exception MedicalRecordNotFoundException when medical record with first name and last name searched doesn't exist
     */
    @Override
    public  void updateMedicalRecord(String firstName, String lastName,MedicalRecord medicalRecord) throws MedicalRecordNotFoundException {

        medicalRecordRepository.updateMedicalRecord(firstName,lastName,medicalRecord);
    }

    /**
     * delete existing medicalRecord
     * @param firstName of medical record to delete
     * @param lastName of medical record to delete
     *
     */
    @Override
    public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        medicalRecordRepository.deleteMedicalRecordByFirstNameAndLastName(firstName,lastName);
    }

}