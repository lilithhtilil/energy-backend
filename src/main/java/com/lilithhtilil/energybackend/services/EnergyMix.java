package com.lilithhtilil.energybackend.services;

import java.time.LocalDateTime;
import java.util.List;

public class EnergyMix {
    public LocalDateTime start;
    public List<Day> days;

    public EnergyMix(LocalDateTime start, List<Day> days) {
        this.start = start;
        this.days = days;
    }

    public static class Day {
        public float avgBiomass;
        public float avgCoal;
        public float avgImports;
        public float avgGas;
        public float avgOther;
        public float avgNuclear;
        public float avgHydro;
        public float avgSolar;
        public float avgWind;
        public float fractionCleanEnergy;

        public Day(float avgBiomass,
                   float avgCoal,
                   float avgImports,
                   float avgGas,
                   float avgOther,
                   float avgNuclear,
                   float avgHydro,
                   float avgSolar,
                   float avgWind,
                   float fractionCleanEnergy) {

            this.avgBiomass = avgBiomass;
            this.avgCoal = avgCoal;
            this.avgImports = avgImports;
            this.avgGas = avgGas;
            this.avgOther = avgOther;
            this.avgNuclear = avgNuclear;
            this.avgHydro = avgHydro;
            this.avgSolar = avgSolar;
            this.avgWind = avgWind;
            this.fractionCleanEnergy = fractionCleanEnergy;
        }
    }
}
