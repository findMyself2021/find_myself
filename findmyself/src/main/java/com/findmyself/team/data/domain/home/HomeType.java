package com.findmyself.team.data.domain.home;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "home_type")
@Getter
@Setter
public class HomeType {

    @Id
    @GeneratedValue
    private Long h_code;

    private double dandok;
    private int apart;
    private int dasede;
    private double officetel;
}