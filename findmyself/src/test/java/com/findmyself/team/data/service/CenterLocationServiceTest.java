package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.CenterLocation;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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
public class CenterLocationServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    CenterLocationService centerLocationService;

    @Test
    public void 압구정동좌표찾기(){
        CenterLocation centerLocation = centerLocationService.findOne((long) 1168054500);
        System.out.println(centerLocation.getLat());
        System.out.println(centerLocation.getLng());
        Assertions.assertEquals(37.530734,centerLocation.getLat());
    }
}
