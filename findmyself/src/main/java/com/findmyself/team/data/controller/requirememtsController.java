package com.findmyself.team.data.controller;

import com.findmyself.team.data.service.ConvenientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class requirememtsController {

    @Autowired
    ConvenientService convenientService;

    @PostMapping("/convenientChange.do")
    public void getConvenientValues(@RequestParam("joy") int joy_value,
                                    @RequestParam("life") int life_value,
                                    @RequestParam("shop") int shop_value,
                                    @RequestParam("sport") int sport_value,
                                    @RequestParam("food") int food_value,
                                    @RequestParam("edu") int edu_value){
//        convenientService.calculateValues(joy_value,life_value,shop_value,
//                sport_value,food_value,edu_value);

    }

}
