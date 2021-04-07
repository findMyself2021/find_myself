package com.findmyself.team.controller;

import com.findmyself.team.data.service.CenterLocationService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final GudongService gudongService;
    private final CenterLocationService centerLocationService;

    private final TrafficService trafficService;

    private String test;
    private String test2;
    private int finishCheck;
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

        return "analysis";
    }

    @RequestMapping(value = "/mapAnalysis2")
    @ResponseBody
    public String getStationInfo(HttpServletRequest httpServletRequest) {

        finishCheck = 0;

        String stations = httpServletRequest.getParameter("sub_Result");
        String carRouteInfo = httpServletRequest.getParameter("car_Result");

        System.out.println("test : " + stations);
        System.out.println("test2 : " + carRouteInfo);

        test = trafficService.searchSubwayInfo(stations);
        test2 = trafficService.searchCarRootInfo(carRouteInfo);
        test += "/" + test2;

        finishCheck = 1;
        return null;
    }

    @RequestMapping(value = "/mapAnalysis3")
    @ResponseBody
    public String postStationInfo() {

        while(true) {
            if(finishCheck == 1)
                break;
        }

        return test;
    }
}
