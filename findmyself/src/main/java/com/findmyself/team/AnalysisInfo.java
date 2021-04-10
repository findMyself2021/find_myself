package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisInfo {

    private int deposit_avg_charter;    //평균 보증금(전세)
    private int deposit_avg_monthly;    //평균 보증금(월세)
    private int monthly_avg_monthly;    //평균 월세(월세)

    private double man_ratio;  //남자 비율
    private double woman_ratio;    //여자 비율

    public AnalysisInfo(int deposit_avg_charter, int deposit_avg_monthly, int monthly_avg_monthly, double man_ratio, double woman_ratio) {
        this.deposit_avg_charter = deposit_avg_charter;
        this.deposit_avg_monthly = deposit_avg_monthly;
        this.monthly_avg_monthly = monthly_avg_monthly;
        this.man_ratio = man_ratio;
        this.woman_ratio = woman_ratio;
    }
}
