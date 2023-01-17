package com.safetynet.alerts.service;


import com.safetynet.alerts.exception.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.MedicalRecord;

import java.util.stream.Stream;

public interface MedicalRecordService {

    Stream<MedicalRecord> getAllMedicalRecords();

    MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

    void updateMedicalRecord(String firstName, String lastName,MedicalRecord medicalRecord) throws MedicalRecordNotFoundException;

    void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
}
