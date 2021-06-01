package com.findmyself.team.data.domain;

import com.findmyself.team.ConvenientTmp;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name= "member")
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    // 카카오 로그인으로 넘어온 값
    private Long id;

    // 가입 날짜
    private String date;

    private String home_type; //charter(전세), monthly(월세)
    private int deposit;
    private int monthly;
    private String address;
    private int traffic;
    private int safety;
    private int sex_ratio;
    private String age_type; // child, s2030, s4050, elder

    private int joy;
    private int life;
    private int shop;
    private int sport;
    private int food;
    private int edu;

    public Member(){
        this.home_type = "monthly";
        this.deposit = 1000;
        this.monthly = 50;
        this.address = "서울 중구 충무로 2";
        this.traffic = 50;
        this.safety = 50;
        this.sex_ratio = 20;
        this.age_type = "s2030";
        this.joy = 40;
        this.life = 40;
        this.shop = 40;
        this.sport = 40;
        this.food = 40;
        this.edu = 40;
    }
    public Member(Long idx, Long id, String date, String home_type, int deposit, int monthly, String address, int traffic, int safety, int sex_ratio, String age_type, int joy, int life, int shop, int sport, int food, int edu) {
        this.idx = idx;
        this.id = id;
        this.date = date;
        this.home_type = home_type;
        this.deposit = deposit;
        this.monthly = monthly;
        this.address = address;
        this.traffic = traffic;
        this.safety = safety;
        this.sex_ratio = sex_ratio;
        this.age_type = age_type;
        this.joy = joy;
        this.life = life;
        this.shop = shop;
        this.sport = sport;
        this.food = food;
        this.edu = edu;
    }
}
