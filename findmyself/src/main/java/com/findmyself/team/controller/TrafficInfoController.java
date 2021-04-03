package com.findmyself.team.controller;

import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import com.findmyself.team.data.domain.traffic.TrafficSubwayLocation;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.traffic.BusLocationService;
import com.findmyself.team.data.service.traffic.SubwayLocationService;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    @PostMapping(value = "/postBusInfo")
    public String loadBusInfo(@RequestParam("resultList") List<String> result){
        System.out.println("크기: "+result.size());
        List<TrafficBusLocation> busList = busLocationService.findAll();
        for(int i=0; i<result.size(); i++){
            //System.out.println(result.get(i));
            busLocationService.updateCodeByLongitude(
                    gudongService.findCodeByDong(result.get(i)),
                    busList.get(i).getLongitude());
        }
        System.out.println("정류장 위치기반 행정동 코드 가져오기 완료");
        return "empty";
    }

    @GetMapping(value = "/getSubwayInfo")    //로그인 화면으로 이동할때(개발중)
    public String openSubwayInfo(Model model) {

        List<TrafficSubwayLocation> subwayList = subwayLocationService.findAll();
        model.addAttribute("subwayList",subwayList);
        return "subwayLocation";
    }

    //@ResponseBody
    //@RequestMapping(path = "/postSubwayInfo")
    @PostMapping(value = "/postSubwayInfo")
    public String loadSubwayInfo(@RequestParam("resultList") String data) throws Exception {
//        System.out.println("크기: "+map.size());
//////        List<TrafficSubwayLocation> subwayList = subwayLocationService.findAll();
//        for(String key : map.keySet()){
//            System.out.println(key+", "+map.get(key));
//            //subwayLocationService.updateCodeByLongitude(Long.parseLong(map.get(key)),Double.parseDouble(key));
//        }
//        System.out.println("지하철역 위치기반 행정동 코드 가져오기 완료");

//        JSONParser jsonParser = new JSONParser();
//        JSONArray insertParam = (JSONArray) jsonParser.parse(data);
//        for(int i=0; i<insertParam.size(); i++) {
//            //배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
//            JSONObject insertData = (JSONObject) insertParam.get(i);
//        }
        return "empty";
    }
}
