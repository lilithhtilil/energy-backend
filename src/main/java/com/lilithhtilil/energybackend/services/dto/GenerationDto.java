package com.lilithhtilil.energybackend.services.dto;

import java.util.List;

public class GenerationDto {
    public List<DataItem> data;

    public GenerationDto(List<DataItem> data) {
        this.data = data;
    }

    public static class DataItem {
        public String from;
        public String to;
        public List<GenerationMixItem> generationmix;

        public DataItem(String from, String to, List<GenerationMixItem> generationmix) {
            this.from = from;
            this.to = to;
            this.generationmix = generationmix;
        }

        public static class GenerationMixItem {
            public String fuel;
            public float perc;

            public GenerationMixItem(String fuel, float perc) {
                this.fuel = fuel;
                this.perc = perc;
            }
        }
    }
}
