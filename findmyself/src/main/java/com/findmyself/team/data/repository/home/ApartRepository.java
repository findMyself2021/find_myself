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

    public HomeApart findOne(Long h_code, String type){
        if(type.equals("charter")){
            return em.createQuery("select ha from HomeApart ha where ha.h_code = :h_code and ha.type like '전세'",HomeApart.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }else{
            System.out.println("아파트 월세가: "+em.createQuery("select ha from HomeApart ha where ha.h_code = :h_code and ha.type like '월세'",HomeApart.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult().getAvg_deposit()+", "+em.createQuery("select ha from HomeApart ha where ha.h_code = :h_code and ha.type like '월세'",HomeApart.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult().getAvg_monthly());
            return em.createQuery("select ha from HomeApart ha where ha.h_code = :h_code and ha.type like '월세'",HomeApart.class)
                    .setParameter("h_code",h_code)
                    .getSingleResult();
        }
    }

    public List<HomeApart> findAll(){
        return em.createQuery("select ha from HomeApart ha",HomeApart.class)
                .getResultList();
    }

    //전세 - 최대 보증금값
    public int findDepositMaxByCharter(){
        return em.createQuery("select max(ha.avg_deposit) from HomeApart ha where ha.type like '전세'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 보증금값
    public int findDepositMaxByMonthly(){
        return em.createQuery("select max(ha.avg_deposit) from HomeApart ha where ha.type like '월세'", Integer.class)
                .getSingleResult();
    }

    //월세 - 최대 월세값
    public int findMonthlyMaxByMonthly(){
        return em.createQuery("select max(ha.avg_monthly) from HomeApart ha where ha.type like '월세'", Integer.class)
                .getSingleResult();
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
