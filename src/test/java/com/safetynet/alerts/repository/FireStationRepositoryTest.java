package com.safetynet.alerts.repository;


import com.safetynet.alerts.dao.impl.DataStorageImpl;
import com.safetynet.alerts.exception.FireStationNotFoundException;
import com.safetynet.alerts.model.AllData;
import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.impl.FireStationRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatStream;


@ExtendWith(SpringExtension.class)
public class FireStationRepositoryTest {
    private final DataStorageImpl dataStorage = new DataStorageImpl();
    private final FireStationRepositoryImpl fireStationRepository = new FireStationRepositoryImpl(dataStorage);
    private List<FireStation> testList;

    @BeforeEach
    public void init(){
        testList = new ArrayList<>();
        testList.add( new FireStation("address1", 101));
        testList.add( new FireStation("address2", 102));
        testList.add( new FireStation("address3", 103));

        AllData allData = new AllData(null, testList, null);
        this.dataStorage.setData(allData);
    }

    @Test
    @DisplayName("getAllFireStation() returns all firestations list")
    public void testGetAllFireStation() {
        //GIVEN
        //WHEN
        final var response = fireStationRepository.getAllFireStation();
        //THEN
        assertThat(response).containsAnyElementsOf(testList);
    }

    @Test
    @DisplayName("findStationNumberByAddress(String address) returns Optional<FireStation> filtered by address")
    public void testFindStationNumberByAddress() {
        //GIVEN
        Optional<FireStation> optionalFireStation = Optional.ofNullable(testList.stream()
                .filter(f -> f.getAddress().equals("address1"))
                .findFirst()
                .orElseThrow(FireStationNotFoundException::new));
        //WHEN

        Optional<FireStation> response = fireStationRepository.findStationNumberByAddress("address1");
        //THEN
        assertThat(Optional.of(response)).hasValue(optionalFireStation);
        assertThat(response.toString()).isEqualTo("Optional[FireStation(address=address1, station=101)]");

    }


    @Test
    @DisplayName("getFireStationsByStationNumber(String number) returns Stream<FireStation> filtered by station number")
    public void testGetAllFireStationByStationNumber() {
        //GIVEN
        var streamFireStation = testList
                .stream()
                .filter(f -> f.getStation().equals(101))
                .toList();
        //WHEN
        Stream<FireStation> response = fireStationRepository.getFireStationsByStationNumber(101);
        //THEN
        assertThatStream(response).isEqualTo(streamFireStation);
    }

    @Test
    @DisplayName("getFireStationsByAddress(String address) returns Stream<FireStation> filtered by address")
    public void testGetAllFireStationByAddress() {
        //GIVEN
        var streamFireStation = testList
                .stream()
                .filter(f -> f.getAddress().equals("address1"))
                .toList();
        //WHEN
        Stream<FireStation> response = fireStationRepository.getFireStationsByAddress("address1");
        //THEN
        assertThatStream(response).isEqualTo(streamFireStation);
    }

    @Test
    @DisplayName("getAllFireStationByStationNumberList(List<String> fireStationNumbers) returns Stream<FireStation> filtered by address")
    public void testGetAllFireStationByStationNumberList() {
        //GIVEN
        final var stationNumberList = new HashSet<Integer>();
        stationNumberList.add(101);
        stationNumberList.add(102);
        var streamFireStation = testList
                .stream()
                .filter(f -> stationNumberList.contains(f.getStation()))
                .toList();
        //WHEN
        Stream<FireStation> response = fireStationRepository.getAllFireStationByStationNumberList(stationNumberList);
        //THEN
        assertThatStream(response).isEqualTo(streamFireStation);
    }

    @Test
    @DisplayName("updateFireStation(FireStation updatedFireStation)  updates fireStation")
    public void testUpdateFireStation() {
        //GIVEN
        FireStation existingFireStation = testList.stream().findFirst().orElseThrow();
        FireStation newFireStation = new FireStation(existingFireStation.getAddress(),existingFireStation.getStation());
        newFireStation.setStation(111);
        assertThat(testList).doesNotContain(newFireStation);

        //WHEN
        fireStationRepository.updateFireStation(newFireStation);
        //THEN
        assertThat(testList).contains(newFireStation);
    }

    @DisplayName("FireStation saveFireStation(FireStation fireStation) saves fireStation")
    @Test
    public void testSaveFireStation() throws IllegalArgumentException{
        //GIVEN
        FireStation newFireStation = new FireStation("address4",104);
        assertThat(testList).doesNotContain(newFireStation);
        //WHEN
        fireStationRepository.saveFireStation(newFireStation);
        //THEN
        assertThat(testList).contains(newFireStation);
        assertThat(testList.toString()).isEqualTo("[FireStation(address=address1, station=101), FireStation(address=address2, station=102), FireStation(address=address3, station=103), FireStation(address=address4, station=104)]");
    }



    @Test
    @DisplayName("deleteFireStationByAddress(String address) deletes fireStation")
    public void testDeleteFireStationByAddress() {
        //GIVEN
        FireStation fireStation = testList.stream().findFirst().orElseThrow(FireStationNotFoundException::new);
        assertThat(testList).contains(fireStation);
        //WHEN
        fireStationRepository.deleteFireStationByAddress(fireStation.getAddress());
        //THEN
        assertThat(testList).doesNotContain(fireStation);
        assertThat(testList.toString()).isEqualTo("[FireStation(address=address2, station=102), FireStation(address=address3, station=103)]");
    }


}