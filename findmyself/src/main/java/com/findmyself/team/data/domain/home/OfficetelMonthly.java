package com.findmyself.team.data.domain.home;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "home_officetel_monthly")
@Getter
@Setter
public class OfficetelMonthly {

    @Id
    @GeneratedValue
    private Long h_code;

    private int deposit;
    private int monthly;
    private int no;
    private int deposit_min;
    private int deposit_max;
    private double deposit_avg;
    private int monthly_min;
    private int monthly_max;
    private double monthly_avg;
}