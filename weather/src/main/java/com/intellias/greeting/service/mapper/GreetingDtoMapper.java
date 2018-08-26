package com.intellias.greeting.service.mapper;

import com.intellias.greeting.service.dto.GreetingDto;
import com.intellias.greeting.service.person.dto.PersonGreetingDto;
import com.intellias.greeting.service.weather.model.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface GreetingDtoMapper {

    @Mappings({
            @Mapping(source = "person.firstName", target = "firstName"),
            @Mapping(source = "person.secondName", target = "secondName"),
            @Mapping(source = "person.message", target = "message"),
            @Mapping(source = "weather.currently.summary", target = "weatherSummary")
    })
    GreetingDto map(PersonGreetingDto person, Weather weather);


}
