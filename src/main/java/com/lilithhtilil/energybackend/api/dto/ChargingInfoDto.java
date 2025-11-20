package com.lilithhtilil.energybackend.api.dto;

public class ChargingInfoDto {
    public String startDateTime;
    public String endDateTime;
    public float avgCleanEnergy;

    public ChargingInfoDto(String startDateTime, String endDateTime, float avgCleanEnergy) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.avgCleanEnergy = avgCleanEnergy;
    }
}
