package com.findmyself.team.data.repository.Home;

import com.findmyself.team.data.domain.HomeApart;
import com.findmyself.team.data.domain.HomeDandok;
import com.findmyself.team.data.domain.HomeDasede;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DasedeRepository {
    @PersistenceContext
    private EntityManager em;

    public HomeDasede findOne(Long h_code){
        return em.find(HomeDasede.class,h_code);
    }

    public List<HomeDasede> findAll(){
        return em.createQuery("select hd from HomeDasede hd",HomeDasede.class)
                .getResultList();
    }

    public List<HomeDasede> findCharters(){
        return em.createQuery("select hd from HomeDasede hd where hd.type like '전세'",HomeDasede.class)
                .getResultList();
    }

    public List<HomeDasede> findMonthly(){
        return em.createQuery("select hd from HomeDasede hd where hd.type like '월세'",HomeDasede.class)
                .getResultList();
    }
}
