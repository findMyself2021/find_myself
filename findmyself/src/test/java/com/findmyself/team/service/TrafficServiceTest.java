package com.findmyself.team.service;

import com.findmyself.team.TrafficData;
import com.findmyself.team.data.service.traffic.SubwayService;
import com.findmyself.team.data.service.traffic.TrafficInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TrafficServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    TrafficService trafficService;

    @Autowired
    TrafficInfoService trafficInfoService;

    @Autowired
    SubwayService subwayService;

    TrafficData td = new TrafficData();

    @Test
    public void 테스트() {
        String stations = "충무로/을지로3가/종로3가/안국/경복궁/독립문/무악재";
        trafficService.searchSubwayInfo(stations);
    }

    @Test
    public void 테스트1(){
       trafficInfoService.findAll();
    }
}
