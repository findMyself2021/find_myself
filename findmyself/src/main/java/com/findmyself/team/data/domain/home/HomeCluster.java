package com.findmyself.team.data.domain.home;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class HomeCluster {

    private int no;
    private int deposit_min;
    private int deposit_max;
    private double deposit_avg;
    private int monthly_min;
    private int monthly_max;
    private double monthly_avg;
    private List<Long> codes;

    public HomeCluster(int no, int deposit_min, int deposit_max, double deposit_avg, List<Long> codes){
        this.no = no;
        this.deposit_min = deposit_min;
        this.deposit_max = deposit_max;
        this.deposit_avg = deposit_avg;
        this.monthly_min = 0;
        this.monthly_max = 0;
        this.monthly_avg = 0;
        this.codes = codes;
    }

    public HomeCluster(int no, int deposit_min, int deposit_max, double deposit_avg, int monthly_min, int monthly_max, double monthly_avg, List<Long> codes) {
        this.no = no;
        this.deposit_min = deposit_min;
        this.deposit_max = deposit_max;
        this.deposit_avg = deposit_avg;
        this.monthly_min = monthly_min;
        this.monthly_max = monthly_max;
        this.monthly_avg = monthly_avg;
        this.codes = codes;
    }
}
