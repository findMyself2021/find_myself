package com.findmyself.team.data.domain.residence.age;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "age_elder")
@Getter
@Setter
public class Elder {

    @Id
    @GeneratedValue
    private Long h_code;

    private double value;
    private int no;
    private double min;
    private double max;
    private double avg;
}