package com.findmyself.team.service;

import com.findmyself.team.TrafficData;
import com.findmyself.team.data.service.Traffic.SubwayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrafficService {

    @Autowired
    SubwayService subwayService;

    TrafficData td = new TrafficData();

    String test = "";

    public String searchSubwayInfo(String subwayStations) {

        //subwayStations 문자열 리스트로 나누고 검색하고 리턴
        String[] stations = subwayStations.split("/");

        //for(int i=0; i<stations.length; i++) {
        for(int i=0; i<stations.length; i++) {
            System.out.println(stations[i]);
            td.setIn_0h(subwayService.findOne(stations[i]).getIn_0h());
            td.setIn_1h(subwayService.findOne(stations[i]).getIn_1h());
            td.setIn_4h(subwayService.findOne(stations[i]).getIn_4h());
            td.setIn_5h(subwayService.findOne(stations[i]).getIn_5h());
            td.setIn_6h(subwayService.findOne(stations[i]).getIn_7h());
            td.setIn_7h(subwayService.findOne(stations[i]).getIn_7h());
            td.setIn_8h(subwayService.findOne(stations[i]).getIn_8h());
            td.setIn_9h(subwayService.findOne(stations[i]).getIn_9h());
            td.setIn_10h(subwayService.findOne(stations[i]).getIn_10h());
            td.setIn_11h(subwayService.findOne(stations[i]).getIn_11h());
            td.setIn_12h(subwayService.findOne(stations[i]).getIn_12h());
            td.setIn_13h(subwayService.findOne(stations[i]).getIn_13h());
            td.setIn_14h(subwayService.findOne(stations[i]).getIn_14h());
            td.setIn_15h(subwayService.findOne(stations[i]).getIn_15h());
            td.setIn_16h(subwayService.findOne(stations[i]).getIn_16h());
            td.setIn_17h(subwayService.findOne(stations[i]).getIn_17h());
            td.setIn_18h(subwayService.findOne(stations[i]).getIn_18h());
            td.setIn_19h(subwayService.findOne(stations[i]).getIn_19h());
            td.setIn_20h(subwayService.findOne(stations[i]).getIn_20h());
            td.setIn_21h(subwayService.findOne(stations[i]).getIn_21h());
            td.setIn_22h(subwayService.findOne(stations[i]).getIn_22h());
            td.setIn_23h(subwayService.findOne(stations[i]).getIn_23h());

            System.out.println(subwayService.findOne(stations[i]).getIn_23h());
        }

        test = Integer.toString(subwayService.findOne(stations[0]).getIn_23h());

        return test;
    }
}
