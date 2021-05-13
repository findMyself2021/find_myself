package com.findmyself.team.controller;

import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.traffic.Traffic;
import com.findmyself.team.data.mapcluster.SafetyClustering;
import com.findmyself.team.data.mapcluster.SafetyLite;
import com.findmyself.team.data.mapcluster.TrafficClustering;
import com.findmyself.team.data.mapcluster.TrafficLite;
import com.findmyself.team.data.service.SafetyService;
import com.findmyself.team.data.service.traffic.TrafficInfoService;
import com.findmyself.team.service.AnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AnalysisService analysisService;
    private final TrafficInfoService trafficInfoService;
    private final SafetyService safetyService;
//    private final TrafficClustering trafficClustering;
//    private final SafetyClustering safetyClustering;

    @GetMapping(value="/")	//로그인 화면으로 이동할때(개발중)
    public String openMain(Model model, HttpServletRequest request) {

        Requirements rq = new Requirements().defaultRequirements();
        List<Long> codeList = null;
        List<DongInfo> topInfoList = null;

        List<Traffic> trafficClustering = trafficInfoService.findAll();
        List<Safety> safetyClustering = safetyService.findAll();

        System.out.println(trafficClustering.get(0).getNo());

        model.addAttribute("rq",rq);
        model.addAttribute("codeList",codeList);
        model.addAttribute("topInfoList", topInfoList);
        model.addAttribute("trafficClustering",trafficClustering);
        model.addAttribute("safetyClustering",safetyClustering);

        try{
            HttpSession session = request.getSession();
            session.setAttribute("id",session.getAttribute("id"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "main";
    }

    @PostMapping(value="/find.do")	//요구 사항 기반 검색
    public String find(Requirements rq, Model model, HttpServletRequest request) {

        List<Long> codeList = analysisService.analysis(rq);
        List<DongInfo> topInfoList = analysisService.findMatchingTop5(rq,codeList);
        List<Traffic> trafficClustering = trafficInfoService.findAll();
        List<Safety> safetyClustering = safetyService.findAll();


        model.addAttribute("rq",rq);
        model.addAttribute("codeList",codeList);
        model.addAttribute("topInfoList", topInfoList);
        model.addAttribute("trafficClustering",trafficClustering);
        model.addAttribute("safetyClustering",safetyClustering);


        try{
            HttpSession session = request.getSession();
            session.setAttribute("id",session.getAttribute("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "main";
    }


}
