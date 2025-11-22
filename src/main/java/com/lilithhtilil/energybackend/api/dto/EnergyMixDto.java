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
        public float coal;
        public float imports;
        public float gas;
        public float other;
        public float nuclear;
        public float hydro;
        public float solar;
        public float wind;
        public float avgCleanEnergy;

        public Day(float biomass,
                   float coal,
                   float imports,
                   float gas,
                   float other,
                   float nuclear,
                   float hydro,
                   float solar,
                   float wind,
                   float avgCleanEnergy) {
            this.biomass = biomass;
            this.coal = coal;
            this.imports = imports;
            this.gas = gas;
            this.other = other;
            this.nuclear = nuclear;
            this.hydro = hydro;
            this.solar = solar;
            this.wind = wind;
            this.avgCleanEnergy = avgCleanEnergy;
        }

    }
}
