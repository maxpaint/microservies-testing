package com.intellias.greeting.api;

import com.intellias.greeting.api.mapper.GreetingVOMapper;
import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.service.Greeting;
import com.intellias.greeting.service.dto.GreetingDto;
import com.intellias.greeting.service.person.PersonGreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/person/greeting")
public class PersonController {

    private final Greeting<GreetingDto> greetingService;

    private final PersonGreetingService personGreeting;

    private final GreetingVOMapper mapper;

    @GetMapping("/{email}")
    public GreetingVO greeting(@PathVariable String email) {
        return mapper.map(greetingService.greeting(email));
    }

    @PutMapping("/{email}/{message}")
    public String updateGreeting(@PathVariable String email, @PathVariable String message) {
        return personGreeting.updateGreeting(email, message);
    }
}
