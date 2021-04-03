package com.findmyself.team.controller;

import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import com.findmyself.team.data.domain.traffic.TrafficSubwayLocation;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.traffic.BusLocationService;
import com.findmyself.team.data.service.traffic.SubwayLocationService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
public class TrafficInfoController {

    @Autowired
    BusLocationService busLocationService;
    @Autowired
    SubwayLocationService subwayLocationService;
    @Autowired
    GudongService gudongService;

    @GetMapping(value = "/getBusInfo")    //로그인 화면으로 이동할때(개발중)
    public String openBusInfo(Model model) {

        List<TrafficBusLocation> busList = busLocationService.findAll();
        model.addAttribute("busList",busList);
        return "busLocation";
    }

    @ResponseBody
    @RequestMapping("/postBusInfo")
    public String loadBusInfo(@RequestBody Map<String, String> param) throws Exception {

        for(String key : param.keySet()){
            //System.out.println(key+", "+param.get(key));
            busLocationService.updateCodeByLongitude(Double.parseDouble(key), Long.parseLong(param.get(key)));
        }
        System.out.println("정류장 좌표기반 행정동 코드 업뎃 완료.");

        return JSONObject.toJSONString(param);
    }

    @GetMapping(value = "/getSubwayInfo")    //로그인 화면으로 이동할때(개발중)
    public String openSubwayInfo(Model model) {

        List<TrafficSubwayLocation> subwayList = subwayLocationService.findAll();
        model.addAttribute("subwayList",subwayList);
        return "subwayLocation";
    }

    @ResponseBody
    @RequestMapping(value="/postSubwayInfo", method=RequestMethod.POST)
    public String loadSubwayInfo(@RequestBody Map<String, String> param) {

        for(String key : param.keySet()){
            System.out.println(key+", "+param.get(key));
            subwayLocationService.updateCodeByLongitude(Double.parseDouble(key), Long.parseLong(param.get(key)));
        }
        System.out.println("지하철역 좌표기반 행정동 코드 업뎃 완료.");

        return JSONObject.toJSONString(param);
    }

}
