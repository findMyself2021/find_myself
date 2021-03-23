package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Requirements {

    private String home_type; //charter(전세), monthly(월세)
    private int deposit;
    private int monthly;
    private int traffic;
    private int convenient;
    private int safety;
    private int sex_ratio;
    private int age_type; // child, 2030s, 4050s, elder
}
