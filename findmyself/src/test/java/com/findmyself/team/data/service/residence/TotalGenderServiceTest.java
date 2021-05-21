package com.findmyself.team.data.service.Residence;

import com.findmyself.team.data.domain.residence.Gender;
import com.findmyself.team.data.domain.residence.GenderCluster;
import com.findmyself.team.data.service.residence.GenderService;
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
public class TotalGenderServiceTest {

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

    @Test
    public void 테스트(){
        System.out.println("결과 행정동 리스트: "+ genderService.analysis(20));
    }

    @Test
    public void 클러스터정보가져오기(){
        List<GenderCluster> genderClusterList = genderService.findClusters();
        for(GenderCluster genderCluster: genderClusterList){
            System.out.println("클러스터 정보: "+ genderCluster);
        }
    }

    @Test
    public void getStd(){
        System.out.println("std_value: "+ genderService.getStdVaule(20));
    }
}