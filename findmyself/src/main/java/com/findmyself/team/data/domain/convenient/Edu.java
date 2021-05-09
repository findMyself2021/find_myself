package com.findmyself.team.data.domain.convenient;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="convenient_edu")
@Getter
@Setter
public class Edu {

    @Id
    @GeneratedValue
    private Long h_code;

    private int value;
    private int no;
    private int min;
    private int max;
    private double avg;
}
