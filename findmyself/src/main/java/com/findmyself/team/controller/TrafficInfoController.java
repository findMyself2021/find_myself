package com.findmyself.team.controller;

import com.findmyself.team.data.domain.traffic.TrafficBus;
import com.findmyself.team.data.service.traffic.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TrafficInfoController {

    @Autowired
    BusService busService;

    @GetMapping(value = "/test01")    //로그인 화면으로 이동할때(개발중)
    public String open(Model model) {

        List<TrafficBus> busList = busService.findAll();

        model.addAttribute("bustList",busList);
        return "bus";
    }

    @PostMapping(value = "/bring")
    public void bringHdong(@RequestParam("h_dong") String h_dong){

        System.out.println("행정동명: "+ h_dong);
    }
}
