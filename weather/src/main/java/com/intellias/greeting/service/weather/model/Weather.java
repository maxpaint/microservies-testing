package com.intellias.greeting.service.weather.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Weather {

    private Double latitude;
    private Double longitude;
    private String timezone;
    private CurrentlyWeather currently;
}
