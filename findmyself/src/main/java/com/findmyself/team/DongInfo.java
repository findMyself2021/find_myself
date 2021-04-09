package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DongInfo {

    private String gu;
    private String h_dong;
    private Long h_code;

    public DongInfo(String gu, String h_dong, Long h_code) {
        this.gu = gu;
        this.h_dong = h_dong;
        this.h_code = h_code;
    }
}
