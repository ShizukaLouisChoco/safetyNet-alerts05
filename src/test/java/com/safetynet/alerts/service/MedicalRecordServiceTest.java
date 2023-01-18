package com.safetynet.alerts.service;


import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.service.impl.MedicalRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class MedicalRecordServiceTest {
    @InjectMocks
    private MedicalRecordServiceImpl medicalRecordService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private List<MedicalRecord> medicalRecordList;

    private MedicalRecord medicalRecord, medicalRecord2;
    @BeforeEach
    public void init(){
        medicalRecordList = new ArrayList<>();
        medicalRecord = new MedicalRecord("adult1","adult1",LocalDate.of(1984,3,6),List.of("aznol:350mg", "hydrapermazol:100mg"),List.of("nillacilan"));
        medicalRecord2 = new MedicalRecord("child1","child1",LocalDate.of(2017,9,6),List.of(),List.of());
        medicalRecordList.add(medicalRecord);
        medicalRecordList.add(medicalRecord2);
    }


    @Test
    @DisplayName("getAllMedicalRecords returns Stream<MedicalRecord>")
    public void testGetAllMedicalRecords(){
        //GIVEN
        //WHEN
        medicalRecordService.getAllMedicalRecords();
        //THEN
        verify(medicalRecordRepository,times(1)).getAllMedicalRecords();
    }

    @Test
    @DisplayName("saveMedicalRecord returns MedicalRecord")
    public void testSaveMedicalRecord(){
        //GIVEN
        //WHEN
        medicalRecordService.saveMedicalRecord(medicalRecord);
        //THEN
        verify(medicalRecordRepository,times(1)).saveMedicalRecord(medicalRecord);

    }

    @Test
    @DisplayName("updateMedicalRecord calls medicalRecordRepository.updateMedicalRecord")
    public void testUpdateMedicalRecord(){
        //GIVEN
        //WHEN
        medicalRecordService.updateMedicalRecord(medicalRecord.getFirstName(),medicalRecord.getLastName(),medicalRecord);
        //THEN
        verify(medicalRecordRepository,times(1)).updateMedicalRecord(medicalRecord.getFirstName(),medicalRecord.getLastName(),medicalRecord);

    }

    @Test
    @DisplayName("deleteMedicalRecordByFirstNameAndLastName calls medicalRecordRepository.deleteMedicalRecordByFirstNameAndLastName")
    public void testDeleteMedicalRecordByFirstNameAndLastName(){
        //GIVEN
        //WHEN
        medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(),medicalRecord.getLastName());
        //THEN
        verify(medicalRecordRepository,times(1)).deleteMedicalRecordByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
    }
}