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
        HomeDandok homeDandok;
        if(type.equals("charter")){
            try{
                homeDandok = em.createQuery("select ha from HomeDandok ha where ha.h_code = :h_code and ha.type like '전세'",HomeDandok.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeDandok;
        }else{
            try{
                homeDandok = em.createQuery("select ha from HomeDandok ha where ha.h_code = :h_code and ha.type like '월세'",HomeDandok.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeDandok;
        }
    }

    public List<HomeDandok> findAll(){
        return em.createQuery("select hd from HomeDandok hd",HomeDandok.class)
                .getResultList();
    }

    public int findDepositMax(){
        return em.createQuery("select max(hd.avg_deposit) from HomeDandok hd", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMax(){
        return em.createQuery("select max(hd.avg_monthly) from HomeDandok hd", Integer.class)
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
