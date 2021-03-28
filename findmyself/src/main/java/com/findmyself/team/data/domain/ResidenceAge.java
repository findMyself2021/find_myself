package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "residence_age")
@Getter
@Setter
public class ResidenceAge {
    @Id
    @GeneratedValue
    private Long h_code;

    private int child;
    private int s2030;
    private int s4050;
    private int elder;
    private int first;
    private int second;


    public ResidenceAge(){

    }
}
