package com.findmyself.team.data.relation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "charter_traffic")
@Setter
@Getter
public class CharterTraffic {
    @Id
    @GeneratedValue
    private Long h_code;

    private int no;
    private double value1;
    private double value2;
    private double min1;
    private double max1;
    private double avg1;
    private double min2;
    private double max2;
    private double avg2;
}
