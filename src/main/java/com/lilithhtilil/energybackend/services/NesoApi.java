package com.lilithhtilil.energybackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lilithhtilil.energybackend.services.dto.GenerationDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class NesoApi {
    public static final DateTimeFormatter NESO_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");
    public RestTemplate restTemplate;
    public ObjectMapper objectMapper;

    public NesoApi(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public EnergyMixIntervals getGeneration(LocalDateTime start, LocalDateTime end){
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
            LocalDateTime leftDate = LocalDateTime.parse(left.from, NESO_DATE_TIME_FORMATTER);
            LocalDateTime rightDate = LocalDateTime.parse(right.from, NESO_DATE_TIME_FORMATTER);
            return leftDate.compareTo(rightDate);
        });

        List<EnergyMixIntervals.Interval> intervals = new ArrayList<>();
        LocalDateTime start = LocalDateTime.parse(sortedData.getFirst().from, NESO_DATE_TIME_FORMATTER);

        for (GenerationDto.DataItem item : sortedData) {
            EnergyMixIntervals.Interval interval = new EnergyMixIntervals.Interval();
            for (GenerationDto.DataItem.GenerationMixItem mixItem : item.generationmix){
                switch (mixItem.fuel) {
                    case "biomass":
                        interval.biomass = mixItem.perc/100f;
                        break;
                    case "coal":
                        interval.coal = mixItem.perc/100f;
                        break;
                    case "imports":
                        interval.imports = mixItem.perc/100f;
                        break;
                    case "gas":
                        interval.gas = mixItem.perc/100f;
                        break;
                    case "other":
                        interval.other = mixItem.perc/100f;
                        break;
                    case "nuclear":
                        interval.nuclear = mixItem.perc/100f;
                        break;
                    case "hydro":
                        interval.hydro = mixItem.perc/100f;
                        break;
                    case "solar":
                        interval.solar = mixItem.perc/100f;
                        break;
                    case "wind":
                        interval.wind = mixItem.perc/100f;
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
