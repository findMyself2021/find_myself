package com.findmyself.team.controller;

import com.findmyself.team.DongInfo;
import com.findmyself.team.data.service.CenterLocationService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.service.TrafficService;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final GudongService gudongService;
    private final CenterLocationService centerLocationService;

    private final TrafficService trafficService;

    private String test;
    private String test2;
    private int pubFinishCheck;
    private int carFinishCheck;

    @PostMapping(value = "/mapAnalysis")
    public String Analysis(HttpServletRequest httpServletRequest, Model model){

        //map.js 에서 넘어온 행정동 코드
        String s_hcode = httpServletRequest.getParameter("hcode");
        long h_code = Long.parseLong(s_hcode);

        //받아온 도착지
        String address = httpServletRequest.getParameter("addr");

        //행정동 중심좌표
        String s_center_x = httpServletRequest.getParameter("center_x");
        Double lat = Double.valueOf(s_center_x);

        String s_center_y = httpServletRequest.getParameter("center_y");
        Double lng = Double.valueOf(s_center_y);

        String listByDistance = httpServletRequest.getParameter("listByDistance");
        System.out.println("!!!!!: "+ listByDistance);

        // 거리1,코드1/거리2,코드2/...
        Map<Double,Long> listByDistanceResult = new HashMap<>();
        String[] setArray1 = listByDistance.split("/"); //거리1,코드1
        String[] setArray2;
        for(int i=0; i<setArray1.length; i++){
            setArray2 = setArray1[i].split(",");
            listByDistanceResult.put(Double.parseDouble(setArray2[0]),Long.parseLong(setArray2[1]));
        }

        List<Double> keys = new ArrayList<>(listByDistanceResult.keySet());
        Collections.sort(keys);

        List<DongInfo> topDisInfoList = new ArrayList<>();
        for(int i=0; i<4; i++){
            double dis = keys.get(i);
            Long code = listByDistanceResult.get(keys.get(i));
            String gu = gudongService.findOne(code).getGu();
            String dong = gudongService.findOne(code).getH_dong();

            System.out.println("distance: "+dis+", h_dong: "+dong);

            DongInfo dongInfo = new DongInfo(gu,dong,code);
            topDisInfoList.add(dongInfo);
        }

        // 테스트중
        String stations = "충무로/을지로3가/종로3가/안국/경복궁/독립문/무악재"; // 분석화면에서 역 이름 문자열 받는다고 가정
        //String test = trafficService.searchSubwayInfo(stations);

        //행정구
        final String gu = gudongService.findOne(h_code).getGu();
        //행정동
        final String h_dong = gudongService.findOne(h_code).getH_dong();

        model.addAttribute("hcode",h_code);
        model.addAttribute("gu",gu);
        model.addAttribute("hdong",h_dong);
        model.addAttribute("addr",address);
        model.addAttribute("center_x",lat);
        model.addAttribute("center_y",lng);
        model.addAttribute("topDisInfoList",topDisInfoList);

        return "analysis";
    }

    @RequestMapping(value = "/mapAnalysis2")
    @ResponseBody
    public String getStationInfo(HttpServletRequest httpServletRequest) {

        pubFinishCheck = 0;

        String stations = httpServletRequest.getParameter("sub_Result");
        System.out.println("test : " + stations);
        test = trafficService.searchSubwayInfo(stations);

        pubFinishCheck = 1;
        return null;
    }

    @RequestMapping(value = "/mapAnalysis3")
    @ResponseBody
    public String postStationInfo() {

        while(true) {
            if(pubFinishCheck == 1)
                break;
        }

        return test;
    }

    @RequestMapping(value = "/mapAnalysis4")
    @ResponseBody
    public String getCarRouteInfo(HttpServletRequest httpServletRequest) {

        carFinishCheck = 0;

        String carRouteInfo = httpServletRequest.getParameter("car_Result");
        System.out.println("test2 : " + carRouteInfo);
        test2 = trafficService.searchCarRootInfo(carRouteInfo);

        carFinishCheck = 1;
        return null;
    }

    @RequestMapping(value = "/mapAnalysis5")
    @ResponseBody
    public String postCarRouteInfo() {

        while(true) {
            if(carFinishCheck == 1)
                break;
        }

        return test2;
    }
}
