package com.safetynet.alerts.repository;


import com.safetynet.alerts.model.MedicalRecord;

import java.util.Optional;
import java.util.stream.Stream;

public interface MedicalRecordRepository {
    Optional<MedicalRecord> findMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

    void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName);

    MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

    Stream<MedicalRecord> getAllMedicalRecords();

    void updateMedicalRecord(String firstName, String lastName,MedicalRecord updatedMedicalRecord);
}