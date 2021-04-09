package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_volume1")
@Getter @Setter
public class TrafficVolume {

    @Id
    @GeneratedValue
    private String num;

    private String name;
    private Long _0h;
    private Long _1h;
    private Long _2h;
    private Long _3h;
    private Long _4h;
    private Long _5h;
    private Long _6h;
    private Long _7h;
    private Long _8h;
    private Long _9h;
    private Long _10h;
    private Long _11h;
    private Long _12h;
    private Long _13h;
    private Long _14h;
    private Long _15h;
    private Long _16h;
    private Long _17h;
    private Long _18h;
    private Long _19h;
    private Long _20h;
    private Long _21h;
    private Long _22h;
    private Long _23h;

    public TrafficVolume() {

    }

}
