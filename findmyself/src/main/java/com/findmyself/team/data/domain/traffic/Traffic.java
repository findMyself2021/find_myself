package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic")
@Getter
@Setter
public class Traffic {

    @Id
    @GeneratedValue
    private Long h_code;

    private int value;
    private int no;
    private int min;
    private int max;
    private double avg;
}
