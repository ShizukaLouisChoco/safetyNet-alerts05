package com.safetynet.alerts.controller;


import com.safetynet.alerts.exception.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicalRecordController {
    private final static Logger logger = LogManager.getLogger("MedicalRecordController");
    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * GetMapping - Get all medical records
     * url : http://localhost:8080/medicalrecords
     * @return all medical records
     */
    @GetMapping("medicalrecords")
    public List<MedicalRecord> getAllMedicalRecords() {
        logger.info(".getAllMedicalRecords");
        logger.info("Accessed endpoint URL:/medicalrecords");
        logger.debug("Request details: GETMapping");

        return medicalRecordService.getAllMedicalRecords().toList();
    }

    /**
     * PostMapping - add a new medical Record
     * url : http://localhost:8080/medicalrecord
     * @param medicalRecord to add
     * @return The medical record saved object
     */
    @PostMapping(value = "medicalrecord")
    public MedicalRecord createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info(".createMedicalRecord");
        logger.info("Accessed endpoint URL:/medicalrecord");
        logger.debug("Request details: POSTMapping, Body medicalRecord :{}", medicalRecord);
        return medicalRecordService.saveMedicalRecord(medicalRecord);
    }

    /**
     * PutMapping - Update an existing medical record
     * url : http://localhost:8080/medicalrecord
     * @return The updated medical record
     */
    @PutMapping("medicalrecord")
    public MedicalRecord updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) throws MedicalRecordNotFoundException{
        logger.info(".updateMedicalRecord");
        logger.info("Accessed endpoint URL:/medicalrecord");
        logger.debug("Request details: PUTMapping, Body medicalRecord :{}", medicalRecord);

        medicalRecordService.updateMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName(),medicalRecord);
        return medicalRecord;
    }

    /**
     * DeleteMapping - Delete a medical record
     * @param firstName of medical record to delete
     * @param lastName of medical record to delete
     */
    @DeleteMapping("medicalrecord")
    public void deleteMedicalRecord(@RequestParam("firstName") final String firstName, @RequestParam("lastName") final String lastName) {
        logger.info(".deleteMedicalRecord");
        logger.info("Accessed endpoint URL:/medicalrecord");
        logger.debug("Request details: DELETEMapping, firstName: {}, lastName: {}",firstName,lastName);

        medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
    }


}