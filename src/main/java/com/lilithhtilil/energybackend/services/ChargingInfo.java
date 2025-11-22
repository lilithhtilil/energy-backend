package com.lilithhtilil.energybackend.services;

import java.time.LocalDateTime;

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
