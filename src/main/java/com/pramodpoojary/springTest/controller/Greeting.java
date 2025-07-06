package com.pramodpoojary.springTest.controller;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Greeting {
    private Long id;
    private String content;
}
