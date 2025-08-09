package com.lpdev.techbuddy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusAPI {

    @GetMapping(value = "/status")
    public String status(){
        return "Tech Buddy is running and if you´re seeing this message, you be authenticaded!";
    }

}