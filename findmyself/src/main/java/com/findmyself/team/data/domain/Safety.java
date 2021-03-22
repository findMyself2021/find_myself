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
    private String gu;
    private Long num;

    public Safety() {

    }
}
