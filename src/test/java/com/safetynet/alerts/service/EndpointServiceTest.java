package com.safetynet.alerts.service;


import com.safetynet.alerts.dto.ChildInfoWithFamilyDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonInfosWithAdultsAndChildrenNumberDTO;
import com.safetynet.alerts.dto.PersonInfosWithFireStationNumberDTO;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.repository.MedicalRecordRepository;
import com.safetynet.alerts.repository.PersonRepository;
import com.safetynet.alerts.service.impl.EndpointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class EndpointServiceTest {

    @InjectMocks
    private EndpointServiceImpl endpointService;
    @Mock
    private FireStationRepository fireStationRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private Person adultPerson;
    private MedicalRecord medicalRecord,medicalRecord2,medicalRecord3,medicalRecord4;

    private FireStation fireStation,fireStation2;

    private List<Person> personList;
    private List<FireStation> fireStationList;
    private List<MedicalRecord> medicalRecordList;
    @BeforeEach
    public void init(){
        personList = new ArrayList<>();
        fireStationList = new ArrayList<>();
        medicalRecordList = new ArrayList<>();
        adultPerson = new Person("adult1", "adult1","address1","Culver","97451","841-874-6512","jaboyd@email.com");
        personList.add(adultPerson);
        fireStation = new FireStation("address1",101);
        fireStation2 = new FireStation("address2",102);
        fireStationList.add(fireStation);
        fireStationList.add(fireStation2);
        medicalRecord = new MedicalRecord("adult1","adult1",LocalDate.of(1984,3,6),List.of("aznol:350mg", "hydrapermazol:100mg"),List.of("nillacilan"));
        medicalRecord2 = new MedicalRecord("child1","child",LocalDate.of(2017,9,6),List.of(),List.of());
        medicalRecord3 = new MedicalRecord("adult2","adult2",LocalDate.of(2000,9,6),List.of(),List.of("shellfish"));
        medicalRecord4 = new MedicalRecord("adult3","adult3",LocalDate.of(1988,3,6),List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"),List.of("peanut", "shellfish", "aznol"));
        medicalRecordList.add(medicalRecord);
        medicalRecordList.add(medicalRecord2);
        medicalRecordList.add(medicalRecord3);
        medicalRecordList.add(medicalRecord4);
        when(personRepository.getAllPerson()).thenReturn(personList.stream());
        when(personRepository.getAllByAddress(fireStation.getAddress())).thenReturn(personList.stream());
        when(fireStationRepository.getAllFireStation()).thenReturn(fireStationList.stream());
        when(fireStationRepository.getFireStationsByStationNumber(fireStation.getStation())).thenReturn(fireStationList.stream());
        when(medicalRecordRepository.getAllMedicalRecords()).thenReturn(medicalRecordList.stream());
        when(medicalRecordRepository.findMedicalRecordByFirstNameAndLastName(adultPerson.getFirstName(), adultPerson.getLastName())).thenReturn(Optional.ofNullable(medicalRecord));

    }

    @Test
    @DisplayName("getPersonInfosWithAdultAndChildrenNumberByFireStationNumber returns PersonInfosWithAdultsAndChildrenNumberDTO")
    public void testGetPersonInfosWithAdultAndChildrenNumberByFireStationNumber() {
        //GIVEN
        Integer fireStationNumber = 101;
        Set<PersonInfoDTO> personInfoDTOSet = new HashSet<>();
        personInfoDTOSet.add(new PersonInfoDTO(adultPerson,medicalRecord));
        //WHEN
        PersonInfosWithAdultsAndChildrenNumberDTO personInfosWithAdultsAndChildrenNumberDTO = null;
        assertThat(personInfosWithAdultsAndChildrenNumberDTO).isNull();
        personInfosWithAdultsAndChildrenNumberDTO = endpointService.getPersonInfosWithAdultAndChildrenNumberByFireStationNumber(fireStationNumber);
        //THEN
        assertThat(personInfosWithAdultsAndChildrenNumberDTO).isNotNull();
    }
    @Test
    @DisplayName("getChildrenInfosWithFamilyByAddress returns Set<ChildInfoWithFamilyDTO>")
    public void testGetChildrenInfosWithFamilyByAddress(){
        //GIVEN
        Set<ChildInfoWithFamilyDTO> childInfoWithFamilyDTOS = new HashSet<>();
        childInfoWithFamilyDTOS = null;
        assertThat(childInfoWithFamilyDTOS).isNull();
        //WHEN
        childInfoWithFamilyDTOS= endpointService.getChildrenInfosWithFamilyByAddress(adultPerson.getAddress());
        //THEN
        assertThat(childInfoWithFamilyDTOS).isNotNull();
    }

    @Test
    @DisplayName("getPhoneNumbersByFireStationNumber returns Set<String>")
    public void testGetPhoneNumbersByFireStationNumber(){
        //GIVEN
        Set<String> setString = new HashSet<>();
        setString = null;
        assertThat(setString).isNull();
        //WHEN
        setString = endpointService.getPhoneNumbersByFireStationNumber(fireStation.getStation());
        //THEN
        assertThat(setString).isNotNull();

    }

    @Test
    @DisplayName("getPersonsOrderedByAddressByStationNumbers returns Map<String, List<PersonInfoDTO>>")
    public void testGetPersonsOrderedByAddressByStationNumbers(){
        //GIVEN
        Map<String,List<PersonInfoDTO>> mapToReturn= new HashMap<>();
        mapToReturn = null;
        assertThat(mapToReturn).isNull();
        Set<Integer> fireStationNumbers = new HashSet<>();
        fireStationNumbers.add(101);
        Set<String> fireStationAddressList = new HashSet<>();
        fireStationAddressList.add("address1");
        //WHEN
        when(fireStationRepository.getAllFireStationByStationNumberList(fireStationNumbers)).thenReturn(fireStationList.stream());
        when(personRepository.getPersonByAddressList(fireStationAddressList)).thenReturn(personList.stream());
        mapToReturn = endpointService.getPersonsOrderedByAddressByStationNumbers(fireStationNumbers);
        //THEN
        assertThat(mapToReturn).isNotNull();
    }

    @Test
    @DisplayName("getPersonInfosByFirstNameOrLastName returns Set<PersonInfoDTO>")
    public void testGetPersonInfosByFirstNameOrLastName(){
        //GIVEN
        Set<PersonInfoDTO> setPersonInfoDTO = new HashSet<>();
        setPersonInfoDTO = null;
        assertThat(setPersonInfoDTO).isNull();
        //WHEN
        when(personRepository.findPersonsByFirstNameOrLastName(adultPerson.getFirstName(), adultPerson.getLastName())).thenReturn(personList.stream());
        setPersonInfoDTO = endpointService.getPersonInfosByFirstNameOrLastName(adultPerson.getFirstName(),adultPerson.getLastName());
        //THEN
        assertThat(setPersonInfoDTO).isNotNull();
    }

    @Test
    @DisplayName("getEmailsByCity returns ")
    public void testGetEmailsByCity(){
        //GIVEN
        Set<String> setString = new HashSet<>();
        setString = null;
        assertThat(setString).isNull();
        //WHEN
        when(personRepository.getAllPersonsByCity("city1")).thenReturn(personList.stream());
        setString = endpointService.getEmailsByCity(adultPerson.getCity());
        //THEN
        assertThat(setString).isNotNull();
    }

    @Test
    @DisplayName("getPersonInfosWithFireStationNumber returns PersonInfosWithFireStationNumberDTO")
    public void testGetPersonInfosWithFireStationNumber(){
        //GIVEN
        PersonInfosWithFireStationNumberDTO personInfosWithFireStationNumberDTO = null;
        assertThat(personInfosWithFireStationNumberDTO).isNull();
        //WHEN
        when(fireStationRepository.findStationNumberByAddress(fireStation.getAddress())).thenReturn(fireStationList.stream().findFirst());
        personInfosWithFireStationNumberDTO = endpointService.getPersonInfosWithFireStationNumber(adultPerson.getAddress());
        //THEN
        assertThat(personInfosWithFireStationNumberDTO).isNotNull();
    }
}