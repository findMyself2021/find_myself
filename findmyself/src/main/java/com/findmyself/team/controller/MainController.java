package com.findmyself.team.controller;

import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.residence.Gender;
import com.findmyself.team.data.domain.traffic.Traffic;
import com.findmyself.team.data.mapcluster.domain.*;
import com.findmyself.team.data.mapcluster.service.*;
import com.findmyself.team.data.service.SafetyService;
import com.findmyself.team.data.service.residence.GenderService;
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
    private final MonthlyService monthlyService;
    private final CharterService charterService;
    private final GenderService genderService;
    private final TotalConvenientService totalConvenientService;
    private final TotalGenderService totalGenderService;
    private final TotalAgeService totalAgeService;

    @GetMapping(value="/")
    public String index(){
        return "index";
    }

    @GetMapping(value="/main")	//로그인 화면으로 이동할때(개발중)
    public String openMain(Requirements rq,Model model, HttpServletRequest request) {

        List<Long> codeList;
        List<DongInfo> topInfoList;

        if(rq.getHome_type() == null){
            rq = new Requirements().defaultRequirements();
            codeList = null;
            topInfoList = null;
        }else{
            codeList = analysisService.analysis(rq);
            topInfoList = analysisService.findMatchingTop(rq,codeList);
        }
        model.addAttribute("rq",rq);
        model.addAttribute("codeList",codeList);
        model.addAttribute("topInfoList", topInfoList);

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
        List<DongInfo> topInfoList = analysisService.findMatchingTop(rq,codeList);

        //매칭률 넘겨주기
        List<Traffic> trafficClustering = trafficInfoService.findAll();
        List<Safety> safetyClustering = safetyService.findAll();
        List<ClusterMonthly> monthlyClustering = monthlyService.findAll();
        List<ClusterCharter> charterClustering = charterService.findAll();
        List<ClusterConvenient> convenientClustering = totalConvenientService.findAll();

        //이건 뭐지?
        List<ClusterGender> genderClustering = totalGenderService.findAll();
        List<Gender> genderClustering_for_map = genderService.findAll();
        List<ClusterAge> ageClustering = totalAgeService.findAll();

        model.addAttribute("rq",rq);
        model.addAttribute("codeList",codeList);
        model.addAttribute("topInfoList", topInfoList);

        model.addAttribute("trafficClustering",trafficClustering);
        model.addAttribute("safetyClustering",safetyClustering);
        model.addAttribute("monthlyClustering",monthlyClustering);
        model.addAttribute("charterClustering",charterClustering);
        model.addAttribute("convenientClustering",convenientClustering);
        //이건뭐지????
        model.addAttribute("genderClustering",genderClustering);
        model.addAttribute("genderClustering_for_map",genderClustering_for_map);
        model.addAttribute("ageClustering",ageClustering);

        model.addAttribute("isLoad",1);

        try{
            HttpSession session = request.getSession();
            session.setAttribute("id",session.getAttribute("id"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "main";
    }


}
