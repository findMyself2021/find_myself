package com.findmyself.team.data.service.residence;

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
public class GenderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    GenderService genderService;

    @Test
    public void 최댓값_가져오기() {
        int min = (int)genderService.findMin();
        int max = (int)genderService.findMax();
        int mid = Math.round((max-min)/2);
        System.out.println("min 값: "+min);
        System.out.println("max 값: "+max);
        System.out.println("mid 값: "+mid);
    }
}