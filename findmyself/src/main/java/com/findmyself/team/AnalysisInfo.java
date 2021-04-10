package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisInfo {
    int deposit_avg;
    int monthly_avg;

    public AnalysisInfo(int deposit_avg, int monthly_avg) {
        this.deposit_avg = deposit_avg;
        this.monthly_avg = monthly_avg;
    }
}
