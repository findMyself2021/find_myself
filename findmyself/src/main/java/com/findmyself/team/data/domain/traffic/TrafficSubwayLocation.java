package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subway_location")
@Getter
@Setter
public class TrafficSubwayLocation {

    private String name;

    @Id
    @GeneratedValue
    private double longitude;

    private double latitude;

    private Long h_code;

    public TrafficSubwayLocation(){}
}


