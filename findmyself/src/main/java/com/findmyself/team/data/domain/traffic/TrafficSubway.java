package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_subway1")
@Getter @Setter
public class TrafficSubway {

    // date, name 기준으로 찾아야함. repository findone 수정 필요!
    @Id
    @GeneratedValue
    private String name;

    private int in_4h;
    private int out_4h;
    private int in_5h;
    private int out_5h;
    private int in_6h;
    private int out_6h;
    private int in_7h;
    private int out_7h;
    private int in_8h;
    private int out_8h;
    private int in_9h;
    private int out_9h;
    private int in_10h;
    private int out_10h;
    private int in_11h;
    private int out_11h;
    private int in_12h;
    private int out_12h;
    private int in_13h;
    private int out_13h;
    private int in_14h;
    private int out_14h;
    private int in_15h;
    private int out_15h;
    private int in_16h;
    private int out_16h;
    private int in_17h;
    private int out_17h;
    private int in_18h;
    private int out_18h;
    private int in_19h;
    private int out_19h;
    private int in_20h;
    private int out_20h;
    private int in_21h;
    private int out_21h;
    private int in_22h;
    private int out_22h;
    private int in_23h;
    private int out_23h;
    private int in_0h;
    private int out_0h;
    private int in_1h;
    private int out_1h;

    public TrafficSubway() {

    }
}
