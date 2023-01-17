package com.safetynet.alerts.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FireStation implements Serializable {

    private String address;

    private Integer station; //todo : rename to stationNumber

    public FireStation update(FireStation fireStation){
        this.address = fireStation.getAddress();
        this.station = fireStation.getStation();
        return fireStation;
    }
}