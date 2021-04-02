package com.findmyself.team.data.domain.home;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "home_apart")
@Getter @Setter
public class HomeApart {

    @Id
    @GeneratedValue
    private Long h_code;

    private String type;
    private int date;
    private int avg_deposit;
    private int avg_monthly;

    public HomeApart(){

    }
}
