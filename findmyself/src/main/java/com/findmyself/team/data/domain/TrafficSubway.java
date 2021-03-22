package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_subway")
@Getter @Setter
public class TrafficSubway {

    @Id
    @GeneratedValue
    private String line;
    private String station;

    private Long date;
    private Long in_4h;
    private Long out_4h;
    private Long in_5h;
    private Long out_5h;
    private Long in_6h;
    private Long out_6h;
    private Long in_7h;
    private Long out_7h;
    private Long in_8h;
    private Long out_8h;
    private Long in_9h;
    private Long out_9h;
    private Long in_10h;
    private Long out_10h;
    private Long in_11h;
    private Long out_11h;
    private Long in_12h;
    private Long out_12h;
    private Long in_13h;
    private Long out_13h;
    private Long in_14h;
    private Long out_14h;
    private Long in_15h;
    private Long out_15h;
    private Long in_16h;
    private Long out_16h;
    private Long in_17h;
    private Long out_17h;
    private Long in_18h;
    private Long out_18h;
    private Long in_19h;
    private Long out_19h;
    private Long in_20h;
    private Long out_20h;
    private Long in_21h;
    private Long out_21h;
    private Long in_22h;
    private Long out_22h;
    private Long in_23h;
    private Long out_23h;
    private Long in_0h;
    private Long out_0h;
    private Long in_1h;
    private Long out_1h;
    private Long in_2h;
    private Long out_2h;

    public TrafficSubway() {

    }
}
