package com.findmyself.team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MapController {

    @PostMapping(value = "/mapAnalysis")
    public String Analysis(){

        return "main";
    }
}
