package com.intellias.greeting.service.weather;

import com.intellias.greeting.config.WeatherServiceConfig;
import com.intellias.greeting.service.weather.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    private final WeatherServiceConfig config;

    public Weather getCurrentWeather() {
        return restTemplate.getForObject(config.getUrl(), Weather.class);
    }


}
