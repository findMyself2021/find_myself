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
        HomeOfficetel homeOfficetel;
        if(type.equals("charter")){
            try{
                homeOfficetel = em.createQuery("select ha from HomeOfficetel ha where ha.h_code = :h_code and ha.type like '전세'",HomeOfficetel.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeOfficetel;
        }else{
            try{
                homeOfficetel = em.createQuery("select ha from HomeOfficetel ha where ha.h_code = :h_code and ha.type like '월세'",HomeOfficetel.class)
                        .setParameter("h_code",h_code)
                        .getSingleResult();
            }catch (Exception e){
                return null;
            }
            return homeOfficetel;
        }
    }

    public List<HomeOfficetel> findAll(){
        return em.createQuery("select ho from HomeOfficetel ho",HomeOfficetel.class)
                .getResultList();
    }

    public int findDepositMax(){
        return em.createQuery("select max(ho.avg_deposit) from HomeOfficetel ho", Integer.class)
                .getSingleResult();
    }

    public int findMonthlyMax(){
        return em.createQuery("select max(ho.avg_monthly) from HomeOfficetel ho", Integer.class)
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
