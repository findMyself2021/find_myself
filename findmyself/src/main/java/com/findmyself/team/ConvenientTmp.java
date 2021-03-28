package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConvenientTmp {
    private int total;  //보기 편하기 위해..(실질적 사용 X)
    private int joy;
    private int life;
    private int shop;
    private int sport;
    private int food;
    private int edu;

    public ConvenientTmp(){ //디폴트 값
        this.total = 40;
        this.joy = 40;
        this.life = 40;
        this.shop = 40;
        this.sport = 40;
        this.food = 40;
        this.edu = 40;
    }
}
