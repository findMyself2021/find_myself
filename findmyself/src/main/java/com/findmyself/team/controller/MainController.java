package com.findmyself.team.controller;

import com.findmyself.team.GudongResult;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.service.Home.HomeService;
import com.findmyself.team.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    AnalysisService analysisService;

    @GetMapping(value="/")	//로그인 화면으로 이동할때(개발중)
    public String openMain(Model model) {

        Requirements rq = new Requirements().defaultRequirements();
        List<GudongResult> codeList = new ArrayList<GudongResult>();
        model.addAttribute("rq",rq);
        model.addAttribute("codeList",codeList);

        return "main";
    }

    @PostMapping(value="/find.do")	//요구 사항 기반 검색
    public String find(Requirements rq, Model model) {

        System.out.println("거주타입: "+rq.getHome_type());
        System.out.println("보증금: "+rq.getDeposit());
        System.out.println("월세: "+rq.getMonthly());
        System.out.println("학교/직장 주소: "  + rq.getAddress());
        System.out.println("교통: "+rq.getTraffic());
        System.out.println("편의시설: "+rq.getConvenient().getTotal());
        System.out.println("오락: "+rq.getConvenient().getJoy());
        System.out.println("안전: "+rq.getSafety());
        System.out.println("남녀성비: "+rq.getSex_ratio());
        System.out.println("거주연령: "+rq.getAge_type());

        List<GudongResult> codeList = analysisService.analysis(rq);
        for(int i=0; i<codeList.size(); i++){
            System.out.println(codeList.get(i).getGu()+","+codeList.get(i).getH_dong()+", "+codeList.get(i).getH_code());
        }

        model.addAttribute("rq",rq);
        model.addAttribute("codeList",codeList);
        model.addAttribute("msg","하이");
        return "main";
    }
}
