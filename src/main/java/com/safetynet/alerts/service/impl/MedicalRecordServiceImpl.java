package com.safetynet.alerts.service.impl;


import com.safetynet.alerts.exception.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {
    private final static Logger logger = LogManager.getLogger("MedicalRecordServiceImpl");

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
        logger.info(".getAllMedicalRecords");

        return medicalRecordRepository.getAllMedicalRecords();
    }

    /**
     * Save a new medicalRecord
     * @param medicalRecord to save
     * @return saved medicalRecord
     */
    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        logger.info(".saveMedicalRecord");

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
        logger.info(".updateMedicalRecord");

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
        logger.info(".deleteMedicalRecordByFirstNameAndLastName");

        medicalRecordRepository.deleteMedicalRecordByFirstNameAndLastName(firstName,lastName);
    }

}