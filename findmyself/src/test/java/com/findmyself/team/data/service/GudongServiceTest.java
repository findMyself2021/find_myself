package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.Gudong;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GudongServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    GudongService gudongService;

    @Test
    public void 청운효자동_찾기() {
        Gudong gudong = gudongService.findOne((long)1111051500);
        System.out.println(gudongService.findOne((long)1111051500).getH_dong());
        assertEquals("1111051500의 행정동 이름은 청운효자동이다.", "청운효자동", gudong.getH_dong());
    }

    @Test
    public void 전체리스트_가져오기() {
        List<Gudong> gudongList = gudongService.findAll();
        for(int i=0; i<gudongList.size(); i++){
            String gu = gudongList.get(i).getGu();
            String dong = gudongList.get(i).getH_dong();
            long h_code = gudongList.get(i).getH_code();

            System.out.println(gu+','+dong+','+h_code);
        }
    }

}