package com.intellias.greeting.service;

import com.intellias.greeting.service.dto.GreetingDto;
import com.intellias.greeting.service.mapper.GreetingDtoMapper;
import com.intellias.greeting.service.person.PersonGreetingService;
import com.intellias.greeting.service.person.dto.PersonGreetingDto;
import com.intellias.greeting.service.weather.WeatherService;
import com.intellias.greeting.service.weather.model.Weather;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GreetingService implements Greeting<GreetingDto> {

    private final WeatherService weatherService;

    private final PersonGreetingService personGreetingService;

    private final GreetingDtoMapper greetingDtoMapper;

    public final GreetingDto greeting(String email) {
        PersonGreetingDto personGreetingDto = personGreetingService.greeting(email);
        Weather currentWeather = weatherService.getCurrentWeather();
        return greetingDtoMapper.map(personGreetingDto, currentWeather);
    }
}
