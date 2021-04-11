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

    public HomeDandok findOne(Long h_code){
        return em.createQuery("select hd from HomeDandok hd where hd.h_code = :h_code",HomeDandok.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public List<HomeDandok> findAll(){
        return em.createQuery("select hd from HomeDandok hd",HomeDandok.class)
                .getResultList();
    }
}
