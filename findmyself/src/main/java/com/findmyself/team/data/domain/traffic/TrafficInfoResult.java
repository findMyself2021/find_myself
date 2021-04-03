package com.findmyself.team.data.domain.traffic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "traffic_info_result")
@Getter
@Setter
public class TrafficInfoResult {

    @Id
    @GeneratedValue
    private Long h_code;

    private int num;
}
