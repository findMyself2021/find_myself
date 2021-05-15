package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnalysisInfo {

    private int deposit_avg_charter;    //평균 보증금(전세)
    private int deposit_avg_monthly;    //평균 보증금(월세)
    private int monthly_avg_monthly;    //평균 월세(월세)

    //거주 유형별 비율
    private double dandok;
    private double apart;
    private double dasede;
    private double officetel;

    private double man_ratio;  //남자 비율
    private double woman_ratio;    //여자 비율

    private double child;
    private double s2030;
    private double s4050;
    private double elder;

    private double matching_ratio;
    private double satisfy_ratio;

    public AnalysisInfo(int deposit_avg_charter, int deposit_avg_monthly, int monthly_avg_monthly, double dandok, double apart, double dasede, double officetel, double man_ratio, double woman_ratio, double child, double s2030, double s4050, double elder, double matching_ratio, double satisfy_ratio) {
        this.deposit_avg_charter = deposit_avg_charter;
        this.deposit_avg_monthly = deposit_avg_monthly;
        this.monthly_avg_monthly = monthly_avg_monthly;
        this.dandok = dandok;
        this.apart = apart;
        this.dasede = dasede;
        this.officetel = officetel;
        this.man_ratio = man_ratio;
        this.woman_ratio = woman_ratio;
        this.child = child;
        this.s2030 = s2030;
        this.s4050 = s4050;
        this.elder = elder;
        this.matching_ratio = matching_ratio;
        this.satisfy_ratio = satisfy_ratio;
    }
}
