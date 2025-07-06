package com.pramodpoojary.springTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingsController {

    AtomicLong counter = new AtomicLong();

    @GetMapping("/greetings")
    public Greeting greetings(@RequestParam(value = "name") String name) {
        Greeting greeting =  new Greeting();
        greeting.setContent("Hello " + name + ", Welcome to Spring Boot!");
        greeting.setId(counter.incrementAndGet());
        return greeting;
    }
}
