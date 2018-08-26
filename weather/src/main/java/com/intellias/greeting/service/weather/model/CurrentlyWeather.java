package com.intellias.greeting.service.weather.model;

import lombok.Data;

@Data
public class CurrentlyWeather {

    private Long time;
    private String summary;
    private Double temperature;
    private Double apparentTemperature;
    private Double pressure;
    private Double windSpeed;
}
