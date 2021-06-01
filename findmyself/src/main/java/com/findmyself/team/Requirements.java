package com.findmyself.team;

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
    private String age_type; // child, s2030, s4050, elder

    public Requirements(){

    }

    public Requirements(String home_type, int deposit, int monthly, String address, int traffic, ConvenientTmp convenient, int safety, int sex_ratio, String age_type) {
        this.home_type = home_type;
        this.deposit = deposit;
        this.monthly = monthly;
        this.address = address;
        this.traffic = traffic;
        this.convenient = convenient;
        this.safety = safety;
        this.sex_ratio = sex_ratio;
        this.age_type = age_type;
    }

    public Requirements defaultRequirements(){ //기본설정값 셋
        Requirements rq = new Requirements();
        
        rq.home_type = "monthly";
        rq.deposit = 1000;
        rq.monthly = 50;
        rq.address = "서울 중구 충무로 2";
        rq.traffic = 50;
        rq.convenient = new ConvenientTmp();
        rq.safety = 50;
        rq.sex_ratio = 20;
        rq.age_type = "s2030";
        
        return rq;
    }
}
