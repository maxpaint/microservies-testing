package com.intellias.greeting.service.weather.model;

import lombok.Data;

@Data
public class Weather {

    private Double latitude;
    private Double longitude;
    private String timezone;
    private CurrentlyWeather currently;
}
