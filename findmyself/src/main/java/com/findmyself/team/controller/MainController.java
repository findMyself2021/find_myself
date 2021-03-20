package com.findmyself.team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value="/")	//로그인 화면으로 이동할때
    public String openSample() {
        return "main";
    }
}
