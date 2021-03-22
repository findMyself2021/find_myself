package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.Convenient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ConvenientServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ConvenientService convenientService;

    @Test
    public void 전체리스트_가져오기() {
        List<Convenient> convenientsList = convenientService.findAll();
        for(int i=0; i<convenientsList.size(); i++){
            long h_code = convenientsList.get(i).getH_code();
            int joy = convenientsList.get(i).getJoy();
            int life = convenientsList.get(i).getLife();
            int shop = convenientsList.get(i).getShop();
            int sport = convenientsList.get(i).getSport();
            int food = convenientsList.get(i).getFood();
            int edu = convenientsList.get(i).getEdu();


            System.out.println(h_code + ',' + joy + ',' + life + ',' + shop + ',' + sport + ',' + food + ',' + edu);
        }
    }
}
