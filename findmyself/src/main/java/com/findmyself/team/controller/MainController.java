package com.findmyself.team.controller;

import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    AnalysisService analysisService;

    @GetMapping(value="/")	//로그인 화면으로 이동할때(개발중)
    public String openMain(Model model) {

        Requirements rq = new Requirements().defaultRequirements();
        List<Long> codeList = null;
        List<DongInfo> topInfoList = null;

        model.addAttribute("rq",rq);
        model.addAttribute("codeList",codeList);
        model.addAttribute("topInfoList", topInfoList);

        return "main";
    }

    @PostMapping(value="/find.do")	//요구 사항 기반 검색
    public String find(Requirements rq, Model model) {

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

        return "main";
    }

    //로그인 관련
    @GetMapping("/login")
    public String openLogin(){
        return "login";
    }

    private String REST_API_KEY = "ac10321dd65139494e4aaa45ff35d52d";
    private String REDIRECT_URI = "http://localhost:8080/login/oauth";

    // 카카오 로그인 인가코드 받기
    @RequestMapping(value = "/login/getKakaoAuthUrl")
    public @ResponseBody String getKakaoAuthUrl() throws Exception {
        String reqUrl = " https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
                + REST_API_KEY
                + "&redirect_uri="
                + REDIRECT_URI
                + "&response_type=code";
        return reqUrl;
    }

    // 카카오 로그인 토큰 받기
    @RequestMapping(value = "/login/oauth")
    public String oauthKakao(@RequestParam("code") String code) throws Exception {
        System.out.println("인가코드: "+code);

        //로그인 및 회원가입 코드 작성

        String reqUrl = " https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id="
                + REST_API_KEY
                + "&redirect_uri="
                + REDIRECT_URI
                + "&code="
                + code;
        return reqUrl;
    }
}
