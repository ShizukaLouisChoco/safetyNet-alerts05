package com.safetynet.alerts.service;


import com.safetynet.alerts.model.FireStation;
import com.safetynet.alerts.repository.FireStationRepository;
import com.safetynet.alerts.service.impl.FireStationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class FireStationServiceTest {
    @InjectMocks
    private FireStationServiceImpl fireStationService;
    @Mock
    private FireStationRepository fireStationRepository;

    private List<FireStation> fireStationList;

    private FireStation fireStation,fireStation2;
    @BeforeEach
    public void init(){
        fireStationList = new ArrayList<>();
        fireStation = new FireStation("address1",101);
        fireStation2 = new FireStation("address2",102);
        fireStationList.add(fireStation);
        fireStationList.add(fireStation2);
        when(fireStationRepository.getAllFireStation()).thenReturn(fireStationList.stream());
    }

    @Test
    @DisplayName("findFireStationByAddress returns Optional<FireStation>")
    public void testFindFireStationByAddress(){
        //GIVEN
        Optional<FireStation> optionalFireStation = fireStationList.stream().findFirst();
        Optional<FireStation> returnedOptionalFireStation = null;
        assertThat(returnedOptionalFireStation).isNull();
        //WHEN
        when(fireStationRepository.findStationNumberByAddress(fireStation.getAddress())).thenReturn(optionalFireStation);
        returnedOptionalFireStation = fireStationService.findFireStationByAddress(fireStation.getAddress());
        //THEN
        assertThat(returnedOptionalFireStation).isNotNull();
        assertThat(optionalFireStation).isSameAs(returnedOptionalFireStation);
    }
    @Test
    @DisplayName("getAllFireStations returns List<FireStation>")
    public void testGetAllFireStations(){
        //GIVEN
        List<FireStation> returnList = null;
        assertThat(returnList).isNull();
        //WHEN
        returnList = fireStationService.getAllFireStations();
        //THEN
        assertThat(returnList).isNotNull();

    }

    @Test
    @DisplayName("saveFireStation returns FireStation")
    public void testSaveFireStation(){
        //GIVEN
        FireStation returnFireStation = null;
        assertThat(returnFireStation).isNull();
        //WHEN
        when(fireStationRepository.saveFireStation(fireStation)).thenReturn(fireStation);
        returnFireStation = fireStationService.saveFireStation(fireStation);
        //THEN
        assertThat(returnFireStation).isNotNull();
        assertThat(returnFireStation).isSameAs(fireStation);
    }
    @Test
    @DisplayName("updateFireStation returns FireStation")
    public void testUpdateFireStation(){
        //GIVEN
        FireStation returnFireStation = null;
        assertThat(returnFireStation).isNull();
        //WHEN
        when(fireStationService.findFireStationByAddress(fireStation.getAddress())).thenReturn(Optional.ofNullable(fireStation));
        returnFireStation = fireStationService.updateFireStation(fireStation.getAddress(),fireStation);
        //THEN
        assertThat(returnFireStation).isNotNull();
        assertThat(returnFireStation).isSameAs(fireStation);
    }
    @Test
    @DisplayName("deleteFireStation deletes FireStation")
    public void testDeleteFireStation(){
        //GIVEN
        //WHEN
        fireStationService.deleteFireStation(fireStation.getAddress());
        //THEN
        verify(fireStationRepository,times(1)).deleteFireStationByAddress(fireStation.getAddress());
    }



}