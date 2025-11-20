package com.lilithhtilil.energybackend.api.dto;

import java.util.List;

public class EnergyMixDto {
    public String startDate;
    public List<Day> days;

    public EnergyMixDto(String startDate, List<Day> days) {
        this.startDate = startDate;
        this.days = days;
    }

    public static class Day {
        public float biomass;
        public float nuclear;
        public float hydro;
        public float wind;
        public float solar;
        public float coal;
        public float gas;
        public float avgCleanEnergy;

        public Day(float biomass, float nuclear, float hydro,
                   float wind, float solar, float coal,
                   float gas, float avgCleanEnergy) {
            this.biomass = biomass;
            this.nuclear = nuclear;
            this.hydro = hydro;
            this.wind = wind;
            this.solar = solar;
            this.coal = coal;
            this.gas = gas;
            this.avgCleanEnergy = avgCleanEnergy;
        }
    }
}
