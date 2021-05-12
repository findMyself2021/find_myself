package com.findmyself.team.controller;

import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.mapcluster.SafetyClustering;
import com.findmyself.team.data.mapcluster.SafetyLite;
import com.findmyself.team.data.mapcluster.TrafficClustering;
import com.findmyself.team.data.mapcluster.TrafficLite;
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
    private final TrafficClustering trafficClustering;
    private final SafetyClustering safetyClustering;

    @GetMapping(value="/")	//로그인 화면으로 이동할때(개발중)
    public String openMain(Model model, HttpServletRequest request) {

        Requirements rq = new Requirements().defaultRequirements();
        List<Long> codeList = null;
        List<DongInfo> topInfoList = null;
        List<TrafficLite> trafficClustering = this.trafficClustering.clusterTraffic();
        List<SafetyLite> safetyClustering = this.safetyClustering.clusterSafety();

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
        List<TrafficLite> trafficClustering = this.trafficClustering.clusterTraffic();
        List<SafetyLite> safetyClustering = this.safetyClustering.clusterSafety();

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
