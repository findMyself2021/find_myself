package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "home_dasede")
@Getter @Setter
public class HomeDasede {

    @Id
    @GeneratedValue
    private Long h_code;

    private String type;
    private int date;
    private int avg_deposit;
    private int avg_monthly;

    public HomeDasede(){

    }
}
