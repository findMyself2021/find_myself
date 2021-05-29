package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "safety")
@Getter @Setter
public class Safety {

    @Id
    @GeneratedValue
    private Long h_code;

    private double value;
    private int no;
    private double min;
    private double max;
    private double avg;
}
