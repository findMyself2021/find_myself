package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "satisfy")
@Getter
@Setter
public class Satisfy {

    @Id
    @GeneratedValue
    private Long h_code;

    private double value;
}