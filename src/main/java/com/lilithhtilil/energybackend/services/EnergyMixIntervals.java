package com.lilithhtilil.energybackend.services;


import java.time.LocalDateTime;
import java.util.List;

public class EnergyMixIntervals {
    public LocalDateTime start;

    /**
     * List of 30-minute intervals, starting from {@link #start} inclusive
     */
    public List<Interval> intervals;

    public EnergyMixIntervals(LocalDateTime start, List<Interval> intervals) {
        this.start = start;
        this.intervals = intervals;
    }

    public static class Interval {
        public float biomass;
        public float coal;
        public float imports;
        public float gas;
        public float other;
        public float nuclear;
        public float hydro;
        public float solar;
        public float wind;
    }
}
