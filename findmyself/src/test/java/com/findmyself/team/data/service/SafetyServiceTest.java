package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.domain.SafetyCrime;
import com.findmyself.team.data.service.Safety.CctvService;
import com.findmyself.team.data.service.Safety.CrimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SafetyServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CrimeService crimeService;

    @Test
    public void 전체리스트_가져오기() {
        List<SafetyCrime> crimeList = crimeService.findAll();
        for(int i=0; i<crimeList.size(); i++){
            String gu = crimeList.get(i).getGu();
            long num = crimeList.get(i).getNum();

            System.out.println(gu + ',' + num);
        }
    }
}
