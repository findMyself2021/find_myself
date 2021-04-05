package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_address1")
@Getter @Setter
public class TrafficAddress {

    @Id
    @GeneratedValue
    private String num;
    private String name;
    private String address;

    private float latitude;
    private float longitude;

    public TrafficAddress() {

    }
}
