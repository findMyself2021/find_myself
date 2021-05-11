package com.findmyself.team.data.repository.mapcluster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrafficLite {
    private Long h_code;
    private int no;

    public TrafficLite(Long h_code, int no) {
        this.h_code = h_code;
        this.no = no;
    }
}
