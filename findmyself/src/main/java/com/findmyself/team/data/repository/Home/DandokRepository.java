package com.findmyself.team.data.repository.Home;

import com.findmyself.team.data.domain.HomeApart;
import com.findmyself.team.data.domain.HomeDandok;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DandokRepository {
    @PersistenceContext
    private EntityManager em;

    public HomeDandok findOne(Long h_code){
        return em.find(HomeDandok.class,h_code);
    }

    public List<HomeDandok> findAll(){
        return em.createQuery("select hd from HomeDandok hd",HomeDandok.class)
                .getResultList();
    }

    public List<HomeDandok> findCharters(){
        return em.createQuery("select hd from HomeDandok hd where hd.type like '전세'",HomeDandok.class)
                .getResultList();
    }

    public List<HomeDandok> findMonthly(){
        return em.createQuery("select hd from HomeDandok hd where hd.type like '월세'",HomeDandok.class)
                .getResultList();
    }
}
