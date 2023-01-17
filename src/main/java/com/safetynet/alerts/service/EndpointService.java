package com.safetynet.alerts.service;


import com.safetynet.alerts.dto.ChildInfoWithFamilyDTO;
import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.dto.PersonInfosWithAdultsAndChildrenNumberDTO;
import com.safetynet.alerts.dto.PersonInfosWithFireStationNumberDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EndpointService {
    PersonInfosWithAdultsAndChildrenNumberDTO getPersonInfosWithAdultAndChildrenNumberByFireStationNumber(Integer fireStationNumber);

    Set<ChildInfoWithFamilyDTO> getChildrenInfosWithFamilyByAddress(String address);

    Set<String> getPhoneNumbersByFireStationNumber( Integer fireStationNumber);

    Map<String, List<PersonInfoDTO>> getPersonsOrderedByAddressByStationNumbers(Set<Integer> fireStationNumbers);

    Set<PersonInfoDTO>  getPersonInfosByFirstNameOrLastName(String firstName, String lastName);

    Set<String> getEmailsByCity(String city) ;

    PersonInfosWithFireStationNumberDTO getPersonInfosWithFireStationNumber(String address);
}