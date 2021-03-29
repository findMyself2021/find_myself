package com.findmyself.team;

import com.findmyself.team.data.domain.Convenient;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Requirements extends ConvenientTmp{


    private String home_type; //charter(전세), monthly(월세)
    private int deposit;
    private int monthly;
    private String address;
    private int traffic;
    private ConvenientTmp convenient;
    private int safety;
    private int sex_ratio;
    private String age_type; // child, s2030s, s4050, elder

    public Requirements(){

    }

    public Requirements defaultRequirements(){ //기본설정값 셋
        Requirements rq = new Requirements();
        
        rq.home_type = "monthly";
        rq.deposit = 1000;
        rq.monthly = 50;
        rq.address = "동국대학교";
        rq.traffic = 50;
        rq.convenient = new ConvenientTmp();
        rq.safety = 50;
        rq.sex_ratio = 20;
        rq.age_type = "s2030";
        
        return rq;
    }
}
