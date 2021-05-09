package com.findmyself.team.data.domain.residence;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GenderCluster {

    private int no;
    private double ratio_min;
    private double ratio_max;
    private double ratio_avg;
    private List<Long> codes;

    public GenderCluster(int no, double ratio_min, double ratio_max, double ratio_avg, List<Long> codes) {
        this.no = no;
        this.ratio_min = ratio_min;
        this.ratio_max = ratio_max;
        this.ratio_avg = ratio_avg;
        this.codes = codes;
    }
}
