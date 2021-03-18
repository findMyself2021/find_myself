package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="convenient")
@Getter @Setter
public class Convenient {

    @Id @GeneratedValue
    private Long h_code;

    private String gu;
    private String h_dong;

    private int joy;
    private int life;
    private int shop;
    private int sport;
    private int food;
    private int edu;

    public Convenient(){

    }
}
