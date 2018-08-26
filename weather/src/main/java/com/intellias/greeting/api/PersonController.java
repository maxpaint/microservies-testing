package com.intellias.greeting.api;

import com.intellias.greeting.api.mapper.GreetingMapper;
import com.intellias.greeting.api.vo.GreetingVO;
import com.intellias.greeting.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/person/greeting")
public class PersonController {

    private final GreetingService greetingService;

    private final GreetingMapper mapper;

    @GetMapping("/{email}")
    public GreetingVO greeting(@PathVariable String email) {
        return mapper.map(greetingService.greeting(email));
    }

    @PutMapping("/{email}/{message}")
    public String updateGreeting(@PathVariable String email, @PathVariable String message) {
        return greetingService.updateGreeting(email, message);
    }
}
