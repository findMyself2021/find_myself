package com.findmyself.team;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class TrafficData {

    private long sx;
    private long sy;
    private long ex;
    private long ey;

    private List<Object> StationName = new ArrayList<Object>();
    private List<Object> StationX = new ArrayList<Object>();
    private List<Object> StationY = new ArrayList<Object>();
}
