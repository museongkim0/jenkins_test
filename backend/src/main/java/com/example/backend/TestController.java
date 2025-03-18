package com.example.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/ex")
    public String test() {
        return "test example 1";
    }

    @GetMapping("/ex2")
    public String test2() {
        return "test example 2";
    }
}
