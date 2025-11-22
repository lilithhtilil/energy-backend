package com.lilithhtilil.energybackend.api;

import com.lilithhtilil.energybackend.api.dto.ChargingInfoDto;
import com.lilithhtilil.energybackend.api.dto.EnergyMixDto;
import com.lilithhtilil.energybackend.services.ChargingInfo;
import com.lilithhtilil.energybackend.services.EnergyMix;
import com.lilithhtilil.energybackend.services.MyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
public class MyController {
    public MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/energy-mix")
    public EnergyMixDto getEnergyMix() {
        LocalDateTime start = LocalDateTime.now();
        EnergyMix energyMix = this.myService.getEnergyMix(start);

        return toDto(energyMix);
    }

    private EnergyMixDto toDto(EnergyMix energyMix) {
        List<EnergyMixDto.Day> days = new ArrayList<>();
        for (EnergyMix.Day day : energyMix.days) {
            EnergyMixDto.Day dayDto = new EnergyMixDto.Day(
                    day.avgBiomass,
                    day.avgCoal,
                    day.avgImports,
                    day.avgGas,
                    day.avgOther,
                    day.avgNuclear,
                    day.avgHydro,
                    day.avgSolar,
                    day.avgWind,
                    day.fractionCleanEnergy);
            days.add(dayDto);
        }

        return new EnergyMixDto(energyMix.start.toString(), days);
    }

    @GetMapping(value = "/charging-info")
    public ChargingInfoDto getChargingInfo(@RequestParam Integer timeFrame) {
        if (timeFrame < 1 || timeFrame > 6) {
            throw new IllegalArgumentException("Invalid time frame");
        }

        ChargingInfo chargingInfo = this.myService.getChargingInfo(timeFrame);
        return toDto(chargingInfo);
    }

    private ChargingInfoDto toDto(ChargingInfo chargingInfo) {
       return new ChargingInfoDto(
               chargingInfo.startDateTime.toString(),
               chargingInfo.endDateTime.toString(),
               chargingInfo.avgCleanEnergy);
    }
}

