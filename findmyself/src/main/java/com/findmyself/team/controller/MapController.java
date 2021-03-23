package com.findmyself.team.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MapController {

    @PostMapping(value = "/mapAnalysis")
    public String Analysis(HttpServletRequest httpServletRequest){

        String s_hcode = httpServletRequest.getParameter("hcode");
        long hcode = Long.parseLong(s_hcode);

        System.out.println(hcode);

        return "analysis";
    }
}
