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

    public ConvenientTmp(){
        this.total = 60;
        this.joy = 10;
        this.life = 10;
        this.shop = 10;
        this.sport = 10;
        this.food = 10;
        this.edu = 10;
    }
}
