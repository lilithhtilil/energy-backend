package com.lilithhtilil.energybackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilithhtilil.energybackend.services.dto.GenerationDto;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

public class NesoApi {
    public RestTemplate restTemplate;
    public ObjectMapper objectMapper;

    public NesoApi(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public EnergyMixIntervals getGeneration(Date start, Date end){
        Map<String, Object> params = new HashMap<>();
        params.put("from", start);
        params.put("to", end);
        GenerationDto result = this.restTemplate.getForObject(
                "https://api.carbonintensity.org.uk/generation/{from}/{to}",
                GenerationDto.class,
                params
        );
        return toDomain(result);
    }

    private EnergyMixIntervals toDomain(GenerationDto value) {
        List<GenerationDto.DataItem> sortedData = new ArrayList<>(value.data);
        sortedData.sort((left, right) -> {
            LocalDateTime leftDate = LocalDateTime.parse(left.from);
            LocalDateTime rightDate = LocalDateTime.parse(right.from);
            return leftDate.compareTo(rightDate);
        });

        List<EnergyMixIntervals.Interval> intervals = new ArrayList<>();
        LocalDateTime start = LocalDateTime.parse(sortedData.getFirst().from);

        for (GenerationDto.DataItem item : sortedData) {
            EnergyMixIntervals.Interval interval = new EnergyMixIntervals.Interval();
            for (GenerationDto.DataItem.GenerationMixItem mixItem : item.generationmix){
                switch (mixItem.fuel) {
                    case "biomass":
                        interval.biomass = mixItem.perc;
                        break;
                    case "coal":
                        interval.coal = mixItem.perc;
                        break;
                    case "imports":
                        interval.imports = mixItem.perc;
                        break;
                    case "gas":
                        interval.gas = mixItem.perc;
                        break;
                    case "other":
                        interval.other = mixItem.perc;
                        break;
                    case "nuclear":
                        interval.nuclear = mixItem.perc;
                        break;
                    case "hydro":
                        interval.hydro = mixItem.perc;
                        break;
                    case "solar":
                        interval.solar = mixItem.perc;
                        break;
                    case "wind":
                        interval.wind = mixItem.perc;
                        break;
                    default:
                        break;
                }
            }
            intervals.add(interval);
        }
        return new EnergyMixIntervals(start, intervals);
    }
}
