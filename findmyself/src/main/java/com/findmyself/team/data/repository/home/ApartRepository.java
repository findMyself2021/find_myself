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
        HomeApart homeApart;
        if(type.equals("charter")){
            try{
                homeApart = em.createQuery("select ha from HomeApart ha where ha.h_code = :h_code and ha.type like '전세'",HomeApart.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeApart;
        }else{
            try{
                homeApart = em.createQuery("select ha from HomeApart ha where ha.h_code = :h_code and ha.type like '월세'",HomeApart.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeApart;
        }
    }

    public List<HomeApart> findAll(){
        return em.createQuery("select ha from HomeApart ha",HomeApart.class)
                .getResultList();
    }

    public int findDepositMax(){
        return em.createQuery("select max(ha.avg_deposit) from HomeApart ha", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMax(){
        return em.createQuery("select max(ha.avg_monthly) from HomeApart ha", Integer.class)
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
