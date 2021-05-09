package com.findmyself.team.data.domain.residence.age;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AgeCluster {

    private int no;
    private int min;
    private int max;
    private double avg;
    private List<Long> codes;

    public AgeCluster(int no, int min, int max, double avg, List<Long> codes) {
        this.no = no;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.codes = codes;
    }
}
