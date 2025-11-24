package com.lilithhtilil.energybackend.services;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EnergyMix {
    public LocalDateTime start;
    public List<Day> days;

    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
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
    }
}
