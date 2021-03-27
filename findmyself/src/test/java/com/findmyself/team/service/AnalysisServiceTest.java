package com.findmyself.team.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.service.ConvenientService;
import com.findmyself.team.data.service.GudongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnalysisServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    AnalysisService analysisService;
    @Autowired
    GudongService gudongService;

    @Test
    public void 필터링된_리스트_반환(){

        Requirements rq = new Requirements().defaultRequirements();
        List<Long> result = analysisService.analysis(rq);
        for(long code:result){
            System.out.println("코드:"+code+", 행정동:"+gudongService.findNameByCode(code));
        }
    }
}