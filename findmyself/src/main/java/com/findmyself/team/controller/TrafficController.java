package com.findmyself.team.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TrafficController {

    @GetMapping("/traffictest")
    public String trafficTest() {
        return "traffic";
    }
}
