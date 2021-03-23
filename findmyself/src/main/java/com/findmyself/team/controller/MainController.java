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

        Requirements requirements = new Requirements();
        model.addAttribute("requirements",requirements);

        return "main";
    }

    @PostMapping(value="/find.do")	//요구 사항 기반 검색
    public String find() {

        return "main";
    }
}
