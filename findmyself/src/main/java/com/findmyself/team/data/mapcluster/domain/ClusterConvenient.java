package com.findmyself.team.data.mapcluster.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cluster_convenient")
@Setter
@Getter
public class ClusterConvenient {

    @Id
    @GeneratedValue
    private Long h_code;

    private int no;
    private double min;
    private double max;
    private double avg;
}
