package com.findmyself.team.controller;

import com.findmyself.team.Requirements;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TrafficController {

    @RequestMapping("/traffictest")
    public String trafficTest(HttpServletRequest request) {
        String title = request.getParameter("title");
        String content  = request.getParameter("content");
        String result = request.getParameter("result");

       /*for(int i=0; i<result.size(); i++) {
            System.out.println("result:" + result[i]);
        }*/
        System.out.println("result:" + result);
        System.out.println("title:" + title);
        System.out.println("content:" + content);

        return "traffic";
    }

    @GetMapping("/traffictest2")
    public String trafficTest2() {
        return "traffic2";
    }

    @GetMapping("/traffictest3")
    public String trafficTest3() {
        return "traffic3";
    }

    @GetMapping("/traffictest4")
    public String trafficTest4(Model model) {
        Requirements rq = new Requirements();
        model.addAttribute("placeName", rq.getAddress());
        return "traffic4";
    }
} 
