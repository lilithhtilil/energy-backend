package com.lilithhtilil.energybackend.services;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {
    public static final int INTERVALS_IN_DAY = 48;

    public final NesoApi nesoApi;

    public MyService(NesoApi nesoApi) {
        this.nesoApi = nesoApi;
    }


    public EnergyMix getEnergyMix(LocalDateTime start) {
        start = start.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plusDays(3).truncatedTo(ChronoUnit.DAYS);

        EnergyMixIntervals result = this.nesoApi.getGeneration(start.plusSeconds(1), end); // Offsets by 1 second to make sure that first interval begin on the given day

        int intervalCount = 0; //there are 48 intervals in one day
        List<EnergyMix.Day> days = new ArrayList<>();

        float sumBiomass = 0;
        float sumCoal = 0;
        float sumImports = 0;
        float sumGas = 0;
        float sumOther = 0;
        float sumNuclear = 0;
        float sumHydro = 0;
        float sumSolar = 0;
        float sumWind = 0;
        for (EnergyMixIntervals.Interval interval : result.intervals) {
            intervalCount++;
            sumBiomass += interval.biomass;
            sumCoal += interval.coal;
            sumImports += interval.imports;
            sumGas += interval.gas;
            sumOther += interval.other;
            sumNuclear += interval.nuclear;
            sumHydro += interval.hydro;
            sumSolar += interval.solar;
            sumWind += interval.wind;

            if (intervalCount == INTERVALS_IN_DAY) {
                float cleanEnergy = sumBiomass
                        + sumNuclear
                        + sumHydro
                        + sumSolar
                        + sumWind;
                float totalEnergy = sumBiomass
                        + sumCoal
                        + sumImports
                        + sumGas
                        + sumOther
                        + sumNuclear
                        + sumHydro
                        + sumSolar
                        + sumWind;
                days.add(new EnergyMix.Day(
                        sumBiomass / INTERVALS_IN_DAY,
                        sumCoal / INTERVALS_IN_DAY,
                        sumImports / INTERVALS_IN_DAY,
                        sumGas / INTERVALS_IN_DAY,
                        sumOther / INTERVALS_IN_DAY,
                        sumNuclear / INTERVALS_IN_DAY,
                        sumHydro / INTERVALS_IN_DAY,
                        sumSolar / INTERVALS_IN_DAY,
                        sumWind / INTERVALS_IN_DAY,
                        cleanEnergy / totalEnergy
                ));
                sumBiomass = 0;
                sumCoal = 0;
                sumImports = 0;
                sumGas = 0;
                sumOther = 0;
                sumNuclear = 0;
                sumHydro = 0;
                sumSolar = 0;
                sumWind = 0;
                intervalCount = 0;
            }

        }
        float cleanEnergy = sumBiomass
                + sumNuclear
                + sumHydro
                + sumSolar
                + sumWind;
        float totalEnergy = sumBiomass
                + sumCoal
                + sumImports
                + sumGas
                + sumOther
                + sumNuclear
                + sumHydro
                + sumSolar
                + sumWind;
        days.add(new EnergyMix.Day(
                sumBiomass / intervalCount,
                sumCoal / intervalCount,
                sumImports / intervalCount,
                sumGas / intervalCount,
                sumOther / intervalCount,
                sumNuclear / intervalCount,
                sumHydro / intervalCount,
                sumSolar / intervalCount,
                sumWind / intervalCount,
                cleanEnergy / totalEnergy
        ));
        return new EnergyMix(start, days);
    }

}
