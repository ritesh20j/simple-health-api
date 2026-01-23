package com.demo.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
public class HealthController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello Ritesh How are you";
    }

    @GetMapping("/time")
    public String time() {
        return LocalDateTime.now().toString();
    }

    @GetMapping("/health")
    public String health() {
        return "UP TO Date";
    }
}