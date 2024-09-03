package com.example.Library.Management.System.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String index() {
        return "index"; // Assuming Thymeleaf or static serving
    }
}
