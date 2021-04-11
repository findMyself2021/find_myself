package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_speed")
@Getter
@Setter
public class TrafficSpeed {
    @Id
    @GeneratedValue
    private String num;
    
    private Float _0h;
    private Float _1h;
    private Float _2h;
    private Float _3h;
    private Float _4h;
    private Float _5h;
    private Float _6h;
    private Float _7h;
    private Float _8h;
    private Float _9h;
    private Float _10h;
    private Float _11h;
    private Float _12h;
    private Float _13h;
    private Float _14h;
    private Float _15h;
    private Float _16h;
    private Float _17h;
    private Float _18h;
    private Float _19h;
    private Float _20h;
    private Float _21h;
    private Float _22h;
    private Float _23h;
    
    public TrafficSpeed() {
        
    }
}
