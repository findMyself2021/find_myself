package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "safety_crime")
@Getter @Setter
public class SafetyCrime {

    @Id
    @GeneratedValue
    private String gu;
    private Long num;

    public SafetyCrime() {

    }
}
