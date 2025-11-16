package com.example.webmvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final GreetingService service;

    public GreetingController(GreetingService service) {
        this.service = service;
    }

    @GetMapping(value = "/greet", produces = MediaType.TEXT_PLAIN_VALUE)
    public String greet(@RequestParam(name = "name", required = false) String name) {
        return service.greet(name);
    }
}
