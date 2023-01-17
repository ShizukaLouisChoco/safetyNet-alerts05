package com.safetynet.alerts.repository;


import com.safetynet.alerts.dao.impl.DataStorageImpl;
import com.safetynet.alerts.exception.MedicalRecordNotFoundException;
import com.safetynet.alerts.model.AllData;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.impl.MedicalRecordRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)

public class MedicalRecordRepositoryTest {

    private final DataStorageImpl dataStorage = new DataStorageImpl();
    private final MedicalRecordRepositoryImpl medicalRecordRepository = new MedicalRecordRepositoryImpl(dataStorage);

    List<MedicalRecord> testList;
    @BeforeEach
    public void init(){
        LocalDate birthdate1 = LocalDate.of(2001,01,01);
        LocalDate birthdate2 = LocalDate.of(2002,02,02);
        List<String> medications1 = new ArrayList<>();
        List<String> medications2 = new ArrayList<>();
        medications1.add("medication1");
        medications2.add("medication2");
        List<String> allergies1 = new ArrayList<>();
        List<String> allergies2 = new ArrayList<>();
        allergies1.add("allergies1");
        allergies2.add("allergies2");

        testList = new ArrayList<>();
        testList.add(new MedicalRecord("first name1","last name1",birthdate1,medications1,allergies1));
        testList.add(new MedicalRecord("first name2","last name2",birthdate2,medications2,allergies2));

        AllData allData = new AllData(null, null, testList);
        this.dataStorage.setAllData(allData);
    }

    @Test
    @DisplayName("getAllMedicalRecords() returns all medical records List<MedicalRecord>")
    public void testGetAllMedicalRecords(){
        //GIVEN

        //WHEN
        Stream<MedicalRecord> response = medicalRecordRepository.getAllMedicalRecords();

        //THEN
        assertThat(response).containsAnyElementsOf(testList);

    }

    @Test
    @DisplayName("findMedicalRecordByFirstNameAndLastName returns Optional<MedicalRecord> filtered by firstName and lastName")
    public void testFindMedicalRecordByFirstNameAndLastName(){
        //GIVEN
        Optional<MedicalRecord> optionalMedicalRecord = Optional.ofNullable(testList.stream()
                .filter(m -> m.getFirstName().equals("first name1") && m.getLastName().equals("last name1"))
                .findFirst()
                .orElseThrow(MedicalRecordNotFoundException::new));
        //WHEN

        Optional<MedicalRecord> response = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName("first name1","last name1");
        //THEN
        assertThat(Optional.of(response)).hasValue(optionalMedicalRecord);
        assertThat(response.toString()).isEqualTo("Optional[MedicalRecord(firstName=first name1, lastName=last name1, birthdate=2001-01-01, medications=[medication1], allergies=[allergies1])]");
    }
    @Test
    @DisplayName("saveMedicalRecord(MedicalRecord medicalRecord) saves medicalRecord and returns MedicalRecord")
    public void testSaveMedicalRecord(){
        //GIVEN
        LocalDate birthdate3 = LocalDate.of(2002,02,02);
        List<String> medications3 = new ArrayList<>();
        medications3.add("medication3");
        List<String> allergies3 = new ArrayList<>();
        allergies3.add("allergies3");
        MedicalRecord medicalRecord3 = new MedicalRecord("first name3","last name3",birthdate3,medications3,allergies3);
        assertThat(testList).doesNotContain(medicalRecord3);
        //WHEN
        medicalRecordRepository.saveMedicalRecord(medicalRecord3);
        //THEN
        assertThat(testList).contains(medicalRecord3);
        assertThat(testList.toString()).isEqualTo("[MedicalRecord(firstName=first name1, lastName=last name1, birthdate=2001-01-01, medications=[medication1], allergies=[allergies1]), MedicalRecord(firstName=first name2, lastName=last name2, birthdate=2002-02-02, medications=[medication2], allergies=[allergies2]), MedicalRecord(firstName=first name3, lastName=last name3, birthdate=2002-02-02, medications=[medication3], allergies=[allergies3])]");
    }


    @Test
    @DisplayName("updateMedicalRecord(MedicalRecord updatedMedicalRecord) updates medicalRecord")
    public void testUpdateMedicalRecord(){
        //GIVEN
        MedicalRecord existingMedicalRecord = testList.stream().findFirst().orElseThrow();
        LocalDate updatingBirthdate = LocalDate.of(1990,02,02);
        MedicalRecord newMedicalRecord = new MedicalRecord(existingMedicalRecord.getFirstName(),existingMedicalRecord.getLastName(),updatingBirthdate,existingMedicalRecord.getMedications(),existingMedicalRecord.getAllergies());
        assertThat(testList).doesNotContain(newMedicalRecord);
        //WHEN
        medicalRecordRepository.updateMedicalRecord(existingMedicalRecord.getFirstName(),existingMedicalRecord.getLastName(),newMedicalRecord);
        //THEN
        assertThat(testList).contains(newMedicalRecord);
        assertThat(testList.toString()).isEqualTo("[MedicalRecord(firstName=first name1, lastName=last name1, birthdate=1990-02-02, medications=[medication1], allergies=[allergies1]), MedicalRecord(firstName=first name2, lastName=last name2, birthdate=2002-02-02, medications=[medication2], allergies=[allergies2])]");
    }

    @Test
    @DisplayName("deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) deletes medicalrecord with firstName and lastName")
    public void testDeleteMedicalRecordByFirstNameAndLastName(){
        //GIVEN
        MedicalRecord deletingMedicalRecord = testList.stream().findFirst().orElseThrow();
        assertThat(testList).contains(deletingMedicalRecord);
        //WHEN
        medicalRecordRepository.deleteMedicalRecordByFirstNameAndLastName(deletingMedicalRecord.getFirstName(),deletingMedicalRecord.getLastName());
        //THEN
        assertThat(testList).doesNotContain(deletingMedicalRecord);
        assertThat(testList.toString()).isEqualTo("[MedicalRecord(firstName=first name2, lastName=last name2, birthdate=2002-02-02, medications=[medication2], allergies=[allergies2])]");
    }

}