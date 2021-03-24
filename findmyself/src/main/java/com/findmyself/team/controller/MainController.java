package com.findmyself.team.controller;

import com.findmyself.team.Requirements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping(value="/")	//로그인 화면으로 이동할때(개발중)
    public String openMain(Model model) {

        Requirements rq = new Requirements().defaultRequirements();
        model.addAttribute("rq",rq);

        return "main";
    }

    @PostMapping(value="/find.do")	//요구 사항 기반 검색
    public String find(Requirements rq, Model model) {

        System.out.println("거주타입: "+rq.getHome_type());
        System.out.println("보증금: "+rq.getDeposit());
        System.out.println("월세: "+rq.getMonthly());
        System.out.println("교통: "+rq.getTraffic());
        System.out.println("편의시설: "+rq.getConvenient());
        System.out.println("안전: "+rq.getSafety());
        System.out.println("남녀성비: "+rq.getSex_ratio());
        System.out.println("거주연령: "+rq.getAge_type());

        model.addAttribute("rq",rq);
        return "main";
    }
}
