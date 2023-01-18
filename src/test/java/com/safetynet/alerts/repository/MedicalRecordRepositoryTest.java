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
        testList = new ArrayList<>();
        testList.add(new MedicalRecord("adult1","adult1",LocalDate.of(1984,3,6),List.of("aznol:350mg", "hydrapermazol:100mg"),List.of("nillacilan")));
        testList.add(new MedicalRecord("child1","child1",LocalDate.of(2017,9,6),List.of(),List.of()));

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
                .filter(m -> m.getFirstName().equals("child1") && m.getLastName().equals("child1"))
                .findFirst()
                .orElseThrow(MedicalRecordNotFoundException::new));
        //WHEN

        Optional<MedicalRecord> response = medicalRecordRepository.findMedicalRecordByFirstNameAndLastName("child1","child1");
        //THEN
        assertThat(Optional.of(response)).hasValue(optionalMedicalRecord);
        assertThat(response.toString()).isEqualTo("Optional[MedicalRecord(firstName=child1, lastName=child1, birthdate=2017-09-06, medications=[], allergies=[])]");
    }
    @Test
    @DisplayName("saveMedicalRecord(MedicalRecord medicalRecord) saves medicalRecord and returns MedicalRecord")
    public void testSaveMedicalRecord(){
        //GIVEN
        MedicalRecord medicalRecord3 = new MedicalRecord("adult2","adult2",LocalDate.of(2000,9,6),List.of(),List.of("shellfish"));
        assertThat(testList).doesNotContain(medicalRecord3);
        //WHEN
        medicalRecordRepository.saveMedicalRecord(medicalRecord3);
        //THEN
        assertThat(testList).contains(medicalRecord3);
        assertThat(testList.toString()).isEqualTo("[MedicalRecord(firstName=adult1, lastName=adult1, birthdate=1984-03-06, medications=[aznol:350mg, hydrapermazol:100mg], allergies=[nillacilan]), MedicalRecord(firstName=child1, lastName=child1, birthdate=2017-09-06, medications=[], allergies=[]), MedicalRecord(firstName=adult2, lastName=adult2, birthdate=2000-09-06, medications=[], allergies=[shellfish])]");
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
        assertThat(testList.toString()).isEqualTo("[MedicalRecord(firstName=adult1, lastName=adult1, birthdate=1990-02-02, medications=[aznol:350mg, hydrapermazol:100mg], allergies=[nillacilan]), MedicalRecord(firstName=child1, lastName=child1, birthdate=2017-09-06, medications=[], allergies=[])]");
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
        assertThat(testList.toString()).isEqualTo("[MedicalRecord(firstName=child1, lastName=child1, birthdate=2017-09-06, medications=[], allergies=[])]");
    }

}