package com.findmyself.team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TrafficInfoController {

    @GetMapping(value = "/test01")    //로그인 화면으로 이동할때(개발중)
    public String open() {

        return "bus";
    }

    @PostMapping(value = "/bring")
    public void bringHdong(@RequestParam("h_dong") String h_dong){
            System.out.println("행정동명: "+ h_dong);
    }
}
