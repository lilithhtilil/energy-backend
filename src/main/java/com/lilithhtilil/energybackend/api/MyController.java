package com.lilithhtilil.energybackend.api;

import com.lilithhtilil.energybackend.api.dto.ChargingInfoDto;
import com.lilithhtilil.energybackend.api.dto.EnergyMixDto;
import com.lilithhtilil.energybackend.services.ChargingInfo;
import com.lilithhtilil.energybackend.services.EnergyMix;
import com.lilithhtilil.energybackend.services.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
public class MyController {
    public MyService myService;

    public MyController(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/api/energy-mix")
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

    @GetMapping(value = "/api/charging-info")
    public ChargingInfoDto getChargingInfo(@RequestParam Integer timeFrame) {
        if (timeFrame < 1 || timeFrame > 6) {
            throw new ValidationException("Invalid time frame");
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public void handleValidationException() {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleMethodArgumentTypeMismatchException() { }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingServletRequestParameterException() { }
}

