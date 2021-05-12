package com.findmyself.team.data.mapcluster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SafetyLite {
    private Long h_code;
    private int no;

    public SafetyLite(Long h_code, int no) {
        this.h_code = h_code;
        this.no = no;
    }
}
