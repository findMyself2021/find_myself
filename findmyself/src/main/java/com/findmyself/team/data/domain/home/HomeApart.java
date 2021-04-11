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

    private int charter_deposit;
    private int monthly_deposit;
    private int monthly_monthly;

    public HomeApart(){

    }
}
