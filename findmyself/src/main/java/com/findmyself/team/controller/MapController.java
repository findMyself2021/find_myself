package com.findmyself.team.controller;

import com.findmyself.team.AnalysisInfo;
import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.relation.domain.CharterTraffic;
import com.findmyself.team.data.relation.domain.GenderSafety;
import com.findmyself.team.data.relation.service.CharterTrafficService;
import com.findmyself.team.data.relation.service.GenderSafetyService;
import com.findmyself.team.data.service.CenterLocationService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.service.AnalysisService;
import com.findmyself.team.service.RelationService;
import com.findmyself.team.service.TrafficService;
import lombok.RequiredArgsConstructor;
import netscape.javascript.JSObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final TrafficService trafficService;
    //상관관계 분석
    private final CharterTrafficService charterTrafficService;
    private final GenderSafetyService genderSafetyService;

    @Autowired
    AnalysisService analysisService;

    @Autowired
    RelationService relationService;

    private String stationInfo;
    private String carInfo;
    private int pubFinishCheck;
    private int carFinishCheck;

    @PostMapping(value = "/mapAnalysis")
    public String Analysis(HttpServletRequest httpServletRequest, Model model){

        //map.js 에서 넘어온 행정동 코드
        String s_hcode = httpServletRequest.getParameter("hcode");
        Long h_code = Long.parseLong(s_hcode);

        //행정동 중심좌표
        String s_center_x = httpServletRequest.getParameter("center_x");
        Double lat = Double.valueOf(s_center_x);

        String s_center_y = httpServletRequest.getParameter("center_y");
        Double lng = Double.valueOf(s_center_y);

        //받아온 도착지
        String address = httpServletRequest.getParameter("addr");

        //상위 20개 행정동의 거리,코드 리스트 받아옴
        // 거리1,코드1/거리2,코드2/...
        String listByDistance = httpServletRequest.getParameter("listByDistance");
        //System.out.println("!!!!!: "+ listByDistance);

        //로그인 한 사용자 id 받아오기
        Long userId = Long.parseLong(httpServletRequest.getParameter("userId"));
        //System.out.println("분석화면으로 넘어온 userid: "+userId);


        //거리 오름차순 top4 계산
        List<DongInfo> topDisInfoList = analysisService.sortTopDis(listByDistance);

        //상세 분석
        AnalysisInfo analysisInfos = analysisService.analysisDetail(h_code,userId);

        //행정동 조회수 분석 --- 개발중
        //analysisService.sortTopClick(userId, h_code);
        //List<DongInfo> resultByClikck = analysisService.analysisTopClick(userId);

        //유사한 행정동 분석
        List<DongInfo> resultBySimilar = analysisService.analysisSimilar(h_code);

        //해당 행정동의 연관관계 => 전체를 끌어와서 그래프를 그릴까...?
        CharterTraffic chartertraffic = charterTrafficService.findOne(h_code);
        GenderSafety gendersafety = genderSafetyService.findOne(h_code);

        //행정구
        final String gu = gudongService.findOne(h_code).getGu();
        //행정동
        final String h_dong = gudongService.findOne(h_code).getH_dong();
        //관계분석 결과
        final String charterTrafficResult = relationService.charterTrafficRelation(h_code);
        final String genderSafetyResult = relationService.genderSafetyRelation(h_code);

        model.addAttribute("hcode",h_code);
        model.addAttribute("gu",gu);
        model.addAttribute("hdong",h_dong);
        model.addAttribute("addr",address);
        model.addAttribute("center_x",lat);
        model.addAttribute("center_y",lng);
        model.addAttribute("topDisInfoList",topDisInfoList);
        model.addAttribute("analysisInfos",analysisInfos);
        //model.addAttribute("resultByClikck",resultByClikck);
        model.addAttribute("resultBySimilar",resultBySimilar);
        model.addAttribute("chartertraffic",chartertraffic);
        model.addAttribute("gendersafety",gendersafety);
        model.addAttribute("chartertrafficLog",charterTrafficResult);
        model.addAttribute("gendersafetyLog",genderSafetyResult);

        return "analysis";
    }

    @RequestMapping(value = "/mapAnalysis2")
    @ResponseBody
    public String getStationInfo(HttpServletRequest httpServletRequest) {

        pubFinishCheck = 0;

        String stations = httpServletRequest.getParameter("sub_Result");
        //System.out.println("test : " + stations);
        stationInfo = trafficService.searchSubwayInfo(stations);

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

        return stationInfo;
    }

    @RequestMapping(value = "/mapAnalysis4")
    @ResponseBody
    public String getCarRouteInfo(HttpServletRequest httpServletRequest) {

        carFinishCheck = 0;

        String carRouteInfo = httpServletRequest.getParameter("car_Result");
        //System.out.println("test2 : " + carRouteInfo);
        carInfo = trafficService.searchCarRootInfo(carRouteInfo);

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

        return carInfo;
    }
}
