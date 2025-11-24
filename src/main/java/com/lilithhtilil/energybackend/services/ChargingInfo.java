package com.lilithhtilil.energybackend.services;

import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode
public class ChargingInfo {
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
    public float avgCleanEnergy;

    public ChargingInfo(LocalDateTime startDateTime, LocalDateTime endDateTime, float avgCleanEnergy) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.avgCleanEnergy = avgCleanEnergy;
    }
}
