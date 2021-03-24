package com.findmyself.team.controller;

import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.service.GudongService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MapController {

    private final GudongService gudongService;

    @PostMapping(value = "/mapAnalysis")
    public String Analysis(HttpServletRequest httpServletRequest, Model model){

        String s_hcode = httpServletRequest.getParameter("hcode");
        long h_code = Long.parseLong(s_hcode);

        //행정구
        final String gu = gudongService.findOne(h_code).getGu();
        //행정동
        final String h_dong = gudongService.findOne(h_code).getH_dong();

        model.addAttribute("hcode",h_code);
        model.addAttribute("gu",gu);
        model.addAttribute("hdong",h_dong);

        return "analysis";
    }
}
