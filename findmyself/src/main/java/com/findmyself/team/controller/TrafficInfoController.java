package com.findmyself.team.controller;

import com.findmyself.team.data.domain.traffic.TrafficBus;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.traffic.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TrafficInfoController {

    @Autowired
    BusService busService;
    @Autowired
    GudongService gudongService;

    @GetMapping(value = "/test01")    //로그인 화면으로 이동할때(개발중)
    public String open(Model model) {

        List<TrafficBus> busList = busService.findAll();
        //System.out.println("사이즈: "+busList.size());
        //for(int i=0; i<busList.size(); i++){
        //    System.out.println(busList.get(i).getName());
        //}
        model.addAttribute("busList",busList);
        return "bus";
    }

    @PostMapping(value = "/send")
    public String loadResult(@RequestParam("result") List<Object> result){
        System.out.println("크기: "+result.size());
        List<TrafficBus> busList = busService.findAll();
        for(int i=0; i<result.size(); i++){
            System.out.println(result.get(i).toString());
            busService.updateCodeByName(
                    gudongService.findCodeByDong(result.get(i).toString()),
                    busList.get(i).getName());
        }
        return "bus";
    }
}
