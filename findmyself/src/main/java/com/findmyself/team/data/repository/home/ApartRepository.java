package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.HomeApart;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ApartRepository {
    @PersistenceContext
    private EntityManager em;

    public HomeApart findOne(Long h_code){
        return em.find(HomeApart.class,h_code);
    }

    public List<HomeApart> findAll(){
        return em.createQuery("select ha from HomeApart ha",HomeApart.class)
                .getResultList();
    }

    public List<HomeApart> findCharters(){
        return em.createQuery("select ha from HomeApart ha where ha.type like '전세'",HomeApart.class)
                .getResultList();
    }

    public List<HomeApart> findMonthly(){
        return em.createQuery("select ha from HomeApart ha where ha.type like '월세'",HomeApart.class)
                .getResultList();
    }
}