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
        Optional<Gudong> gudong = gudongService.findOne((long)1111051500);
        assertEquals("1111051500의 행정동 이름은 청운효자동이다.", "청운효자동", gudong.get().getH_dong());
    }

}