package com.findmyself.team.controller;

import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.service.TrafficService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final GudongService gudongService;

    private final TrafficService trafficService;

    @PostMapping(value = "/mapAnalysis")
    public String Analysis(HttpServletRequest httpServletRequest, Model model){

        String s_hcode = httpServletRequest.getParameter("hcode");
        long h_code = Long.parseLong(s_hcode);

        String address = httpServletRequest.getParameter("addr");

        // 테스트중
        String stations = "충무로/을지로3가/종로3가/안국/경복궁/독립문/무악재"; // 분석화면에서 역 이름 문자열 받는다고 가정
        String test = trafficService.searchSubwayInfo(stations); 

        //행정구
        final String gu = gudongService.findOne(h_code).getGu();
        //행정동
        final String h_dong = gudongService.findOne(h_code).getH_dong();

        model.addAttribute("hcode",h_code);
        model.addAttribute("gu",gu);
        model.addAttribute("hdong",h_dong);
        model.addAttribute("addr",address);

        return "analysis";
    }
}
