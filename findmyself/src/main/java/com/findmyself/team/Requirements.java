package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Requirements {

    private String home_type; //charter(전세), monthly(월세)
    private int deposit;
    private int monthly;
    private int traffic;
    private int convenient;
    private int safety;
    private int sex_ratio;
    private String age_type; // child, 2030s, 4050s, elder

    public Requirements(){

    }

    public Requirements defaultRequirements(){ //기본설정값 셋
        Requirements rq = new Requirements();
        
        rq.home_type = "monthly";
        rq.deposit = 1000;
        rq.monthly = 50;
        rq.traffic = 50;
        rq.convenient = 50;
        rq.safety = 50;
        rq.sex_ratio = 50;
        rq.age_type = "2030s";
        
        return rq;
    }
}
