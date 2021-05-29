package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "all_cluster_charter")
@Getter
@Setter
public class AllByCharter {

    @Id
    @GeneratedValue
    private Long h_code;

    private double component1;
    private double component2;
    private double component3;

    private int no;
}
