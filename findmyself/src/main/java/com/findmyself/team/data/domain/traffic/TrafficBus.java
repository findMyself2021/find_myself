package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bus")
@Getter
@Setter
public class TrafficBus {

    @Id
    @GeneratedValue
    private String name;

    private double x;
    private double y;
    private Long h_code;
}
