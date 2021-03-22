package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Requirements {

    private String home_type;
    private int deposit;
    private int monthly;
    private int traffic;
    private int convenient;
    private int sex_ratio;
    private int age_type; // 1:child,2:2030s,3:4050s, 4:elder
}
