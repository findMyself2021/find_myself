package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntervalData { //필터링에 따른 인터벌 데이터 클래스
    
    int interval;
    Long h_code;

    public IntervalData(int interval, Long h_code) {
        this.interval = interval;
        this.h_code = h_code;
    }
}
