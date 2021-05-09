package com.findmyself.team.data.service.home;

import com.findmyself.team.data.repository.home.ApartRepository;
import com.findmyself.team.data.repository.home.DandokRepository;
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
public class HomeServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    HomeService homeService;

    @Autowired
    ApartRepository apartRepository;

    @Autowired
    DandokRepository dandokRepository;

}