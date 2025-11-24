package com.lilithhtilil.energybackend.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MyServiceTest {

    @Test
    void getsEnergyMix() {
        // given
        NesoApi nesoApi = mock(NesoApi.class);
        EnergyMixIntervals nesoApiResult = new EnergyMixIntervals(
                LocalDateTime.parse("2025-01-01T00:00:00"),
                intervalList(134));
        nesoApiResult.intervals.get(0).hydro = 0.3f;
        nesoApiResult.intervals.get(50).other = 0.4f;
        nesoApiResult.intervals.get(100).coal = 0.8f;
        when(nesoApi.getGeneration(any(), any())).thenReturn(nesoApiResult);

        LocalDateTime start = LocalDateTime.parse("2025-01-01T10:09:00");

        // when
        MyService myService = new MyService(nesoApi);
        EnergyMix result = myService.getEnergyMix(start);

        // then
        EnergyMix expected = new EnergyMix(
                LocalDateTime.parse("2025-01-01T00:00:00"),
                List.of(
                        new EnergyMix.Day(0.01f,
                                0.01f,
                                0.20f,
                                0.20f,
                                0.01f,
                                0.01f,
                                0.016f,
                                0.05f,
                                0.50f,
                                0.582f),
                        new EnergyMix.Day(
                                0.01f,
                                0.01f,
                                0.20f,
                                0.20f,
                                0.018f,
                                0.01f,
                                0.01f,
                                0.05f,
                                0.50f,
                                0.575f),
                        new EnergyMix.Day(
                                0.01f,
                                0.03f,
                                0.20f,
                                0.20f,
                                0.01f,
                                0.01f,
                                0.01f,
                                0.05f,
                                0.50f,
                                0.568f
                        )));
        Assertions.assertEquals(expected.start, result.start);
        Assertions.assertEquals(expected.days.size(), result.days.size());
        for(int i = 0; i < expected.days.size(); ++i) {
            assertEquals(expected.days.get(i), result.days.get(i), "Difference at day index " + i);
        }
    }

    void assertEquals(EnergyMix.Day expected, EnergyMix.Day actual, String message) {
        Assertions.assertEquals(expected.avgBiomass, actual.avgBiomass, 0.001f, message+", avgBiomass");
        Assertions.assertEquals(expected.avgCoal, actual.avgCoal, 0.001f, message+", avgCoal");
        Assertions.assertEquals(expected.avgImports, actual.avgImports, 0.001f, message+", avgImports");
        Assertions.assertEquals(expected.avgGas, actual.avgGas, 0.001f, message+", avgGas");
        Assertions.assertEquals(expected.avgOther, actual.avgOther, 0.001f, message+", avgOther");
        Assertions.assertEquals(expected.avgNuclear, actual.avgNuclear, 0.001f, message+", avgNuclear");
        Assertions.assertEquals(expected.avgHydro, actual.avgHydro, 0.001f, message+", avgHydro");
        Assertions.assertEquals(expected.avgSolar, actual.avgSolar, 0.001f, message+", avgSolar");
        Assertions.assertEquals(expected.avgWind, actual.avgWind, 0.001f, message+", avgWind");
        Assertions.assertEquals(expected.fractionCleanEnergy, actual.fractionCleanEnergy, 0.001f, message+", fractionCleanEnergy");
    }

    @Test
    void getsChargingInfo() {
        NesoApi nesoApi = mock(NesoApi.class);
        EnergyMixIntervals nesoApiResult = new EnergyMixIntervals(
                LocalDateTime.parse("2025-01-01T00:00:00"),
                intervalList(86));
        nesoApiResult.intervals.get(7).hydro = 0.3f;
        when(nesoApi.getGeneration(any(), any())).thenReturn(nesoApiResult
        );
        LocalDateTime start = LocalDateTime.parse("2025-01-01T10:09:00");
        int timeFrame = 3;

        MyService myService = new MyService(nesoApi);
        ChargingInfo result = myService.getChargingInfo(timeFrame, start);

        ChargingInfo expected = new ChargingInfo(
                LocalDateTime.parse("2025-01-02T01:00:00"),
                LocalDateTime.parse("2025-01-02T04:00:00"),
                0.628f
        );
        Assertions.assertEquals(expected.startDateTime, result.startDateTime);
        Assertions.assertEquals(expected.endDateTime, result.endDateTime);
        Assertions.assertEquals(expected.avgCleanEnergy, result.avgCleanEnergy, 0.001f);
    }

    List<EnergyMixIntervals.Interval> intervalList(int length) {
        List<EnergyMixIntervals.Interval> intervals = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            EnergyMixIntervals.Interval interval = new EnergyMixIntervals.Interval();
            interval.biomass = 0.01f;
            interval.coal = 0.01f;
            interval.imports = 0.20f;
            interval.gas = 0.20f;
            interval.other = 0.01f;
            interval.nuclear = 0.01f;
            interval.hydro = 0.01f;
            interval.solar = 0.05f;
            interval.wind = 0.50f;
            intervals.add(interval);
        }
        return intervals;
    }


}
