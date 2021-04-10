package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.HomeApart;
import com.findmyself.team.data.domain.home.HomeDandok;
import com.findmyself.team.data.domain.home.HomeDasede;
import com.findmyself.team.data.domain.home.HomeOfficetel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class DandokRepository {
    @PersistenceContext
    private EntityManager em;

    public HomeDandok findOne(Long h_code, String type){
        if(type.equals("charter")){
            return em.createQuery("select hd from HomeDandok hd where hd.h_code = :h_code and hd.type like '전세'",HomeDandok.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }else{
            return em.createQuery("select hd from HomeDandok hd where hd.h_code = :h_code and hd.type like '월세'",HomeDandok.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }
    }

    public List<HomeDandok> findAll(){
        return em.createQuery("select hd from HomeDandok hd",HomeDandok.class)
                .getResultList();
    }

    //전세 - 최대 보증금값
    public int findDepositMaxByCharter(){
        return em.createQuery("select max(ha.avg_deposit) from HomeDandok ha where ha.type like '전%'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 보증금값
    public int findDepositMaxByMonthly(){
        return em.createQuery("select max(ha.avg_deposit) from HomeDandok ha where ha.type like '월%'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 월세값
    public int findMonthlyMaxByMonthly(){
        return em.createQuery("select max(ha.avg_monthly) from HomeDandok ha where ha.type like '월세'", Integer.class)
                .getSingleResult();
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
