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
        HomeDasede homeDasede;
        if(type.equals("charter")){
            try{
                homeDasede = em.createQuery("select ha from HomeDasede ha where ha.h_code = :h_code and ha.type like '전세'",HomeDasede.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeDasede;
        }else{
            try{
                homeDasede = em.createQuery("select ha from HomeDasede ha where ha.h_code = :h_code and ha.type like '월세'",HomeDasede.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeDasede;
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
