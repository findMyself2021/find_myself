package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.service.AnalysisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BusServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BusLocationService busLocationService;
    @Autowired
    GudongService gudongService;

    @Test
    public void 버스데이터변환() {
        System.out.println("코드: "+gudongService.findCodeByDong("혜화동"));
    }

}