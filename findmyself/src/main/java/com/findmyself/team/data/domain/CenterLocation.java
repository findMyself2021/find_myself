package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "center_lat_lng")
@Getter @Setter
public class CenterLocation {

    @Id
    @GeneratedValue
    long h_code;

    double Lat;
    double Lng;

    public CenterLocation(){

    }
}
