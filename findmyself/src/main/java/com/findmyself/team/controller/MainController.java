package com.findmyself.team.controller;

import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    AnalysisService analysisService;

    @GetMapping(value="/")	//로그인 화면으로 이동할때(개발중)
    public String openMain(Model model, HttpServletRequest request) {

        Requirements rq = new Requirements().defaultRequirements();
        List<Long> codeList = null;
        List<DongInfo> topInfoList = null;

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

//        System.out.println("거주타입: "+rq.getHome_type());
//        System.out.println("보증금: "+rq.getDeposit());
//        System.out.println("월세: "+rq.getMonthly());
//        System.out.println("학교/직장 주소: "  + rq.getAddress());
//        System.out.println("교통: "+rq.getTraffic());
//        System.out.println("편의시설: "+rq.getConvenient().getTotal());
//        System.out.println("오락: "+rq.getConvenient().getJoy());
//        System.out.println("안전: "+rq.getSafety());
//        System.out.println("남녀성비: "+rq.getSex_ratio());
//        System.out.println("거주연령: "+rq.getAge_type());

        List<Long> codeList = analysisService.analysis(rq);
        List<DongInfo> topInfoList = analysisService.findMatchingTop5(rq,codeList);

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


}
