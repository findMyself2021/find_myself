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

    public HomeDasede findOne(Long h_code){
        return em.createQuery("select hd from HomeDasede hd where hd.h_code = :h_code",HomeDasede.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public List<HomeDasede> findAll(){
        return em.createQuery("select hd from HomeDasede hd",HomeDasede.class)
                .getResultList();
    }
}
