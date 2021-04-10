package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.HomeDandok;
import com.findmyself.team.data.domain.home.HomeDasede;
import com.findmyself.team.data.domain.home.HomeOfficetel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OfficetelRepository {
    @PersistenceContext
    private EntityManager em;

    public HomeOfficetel findOne(Long h_code, String type){
        if(type.equals("charter")){
            return em.createQuery("select ho from HomeOfficetel ho where ho.h_code = :h_code and ho.type like '전%'",HomeOfficetel.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }else{
            return em.createQuery("select ho from HomeOfficetel ho where ho.h_code = :h_code and ho.type like '월%'",HomeOfficetel.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }
    }

    public List<HomeOfficetel> findAll(){
        return em.createQuery("select ho from HomeOfficetel ho",HomeOfficetel.class)
                .getResultList();
    }

    //전세 - 최대 보증금값
    public int findDepositMaxByCharter(){
        return em.createQuery("select max(ha.avg_deposit) from HomeOfficetel ha where ha.type like '전세'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 보증금값
    public int findDepositMaxByMonthly(){
        return em.createQuery("select max(ha.avg_deposit) from HomeOfficetel ha where ha.type like '월세'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 월세값
    public int findMonthlyMaxByMonthly(){
        return em.createQuery("select max(ha.avg_monthly) from HomeOfficetel ha where ha.type like '월세'", Integer.class)
                .getSingleResult();
    }

    public List<HomeOfficetel> findCharters(){
        return em.createQuery("select ho from HomeOfficetel ho where ho.type like '전세'",HomeOfficetel.class)
                .getResultList();
    }

    public List<HomeOfficetel> findMonthly(){
        return em.createQuery("select ho from HomeOfficetel ho where ho.type like '월세'",HomeOfficetel.class)
                .getResultList();
    }
}
