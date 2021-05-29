package com.findmyself.team.data.repository.traffic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TrafficCluster {

    private int no;
    private double min;
    private double max;
    private double avg;
    private List<Long> codes;

    public TrafficCluster(int no, double min, double max, double avg, List<Long> codes) {
        this.no = no;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.codes = codes;
    }
}