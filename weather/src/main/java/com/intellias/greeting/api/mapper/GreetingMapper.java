package com.intellias.greeting.api.mapper;

import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.service.dto.GreetingDto;
import org.mapstruct.Mapper;

@Mapper
public interface GreetingMapper {

    GreetingVO map(GreetingDto source);
}
