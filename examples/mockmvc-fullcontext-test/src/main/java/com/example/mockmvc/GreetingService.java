package com.example.mockmvc;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    public String greet(String name) {
        String who = (name == null || name.isBlank()) ? "World" : name.trim();
        return "Hello, " + who + "!";
    }
}
