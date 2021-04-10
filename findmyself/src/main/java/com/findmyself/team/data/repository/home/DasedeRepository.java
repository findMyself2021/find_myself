package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.HomeApart;
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

    public HomeDasede findOne(Long h_code, String type){
        if(type.equals("charter")){
            return em.createQuery("select hd from HomeDasede hd where hd.h_code = :h_code and hd.type like '전세'",HomeDasede.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }else{
            return em.createQuery("select hd from HomeDasede hd where hd.h_code = :h_code and hd.type like '월세'",HomeDasede.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }
    }

    public List<HomeDasede> findAll(){
        return em.createQuery("select hd from HomeDasede hd",HomeDasede.class)
                .getResultList();
    }

    //전세 - 최대 보증금값
    public int findDepositMaxByCharter(){
        return em.createQuery("select max(ha.avg_deposit) from HomeDasede ha where ha.type like '전%'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 보증금값
    public int findDepositMaxByMonthly(){
        return em.createQuery("select max(ha.avg_deposit) from HomeDasede ha where ha.type like '월%'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 월세값
    public int findMonthlyMaxByMonthly(){
        return em.createQuery("select max(ha.avg_monthly) from HomeDasede ha where ha.type like '월세'", Integer.class)
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
