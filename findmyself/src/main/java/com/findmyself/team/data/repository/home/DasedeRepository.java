package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.HomeDandok;
import com.findmyself.team.data.domain.home.HomeDasede;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DasedeRepository {
    @PersistenceContext
    private EntityManager em;

    public HomeDasede findOne(Long h_code){
        if(em.find(HomeDasede.class,h_code) == null){
            return null;
        }else{
            return em.find(HomeDasede.class,h_code);
        }
    }

    public List<HomeDasede> findAll(){
        return em.createQuery("select hd from HomeDasede hd",HomeDasede.class)
                .getResultList();
    }

    public int findDepositMax(){
        return em.createQuery("select max(hd.avg_deposit) from HomeDasede hd", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMax(){
        return em.createQuery("select max(hd.avg_monthly) from HomeDasede hd", Integer.class)
                .getSingleResult();
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
