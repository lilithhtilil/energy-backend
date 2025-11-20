package com.lilithhtilil.energybackend.api;

import com.lilithhtilil.energybackend.api.dto.ChargingInfoDto;
import com.lilithhtilil.energybackend.api.dto.EnergyMixDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MyController {

    @GetMapping("/energy-mix")
    public EnergyMixDto getEnergyMix() {
        return new EnergyMixDto(
                "20/11/2025",
                List.of(new EnergyMixDto.Day(
                        0.05f,
                        0.15f,
                        0.1f,
                        0.08f,
                        0.02f,
                        0.2f,
                        0.4f,
                        0.4f
                )));
    }

    @GetMapping(value = "/charging-info")
    public ChargingInfoDto getChargingInfo(@RequestParam Integer timeFrame) {
        return new ChargingInfoDto(
                "2025-11-20T10:00:00Z","2025-11-20T10:00:00Z", 0.4f
        );
    }
}

