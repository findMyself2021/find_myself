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

    public HomeOfficetel findOne(Long h_code){
        return em.createQuery("select ho from HomeOfficetel ho where ho.h_code = :h_code",HomeOfficetel.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public List<HomeOfficetel> findAll(){
        return em.createQuery("select ho from HomeOfficetel ho",HomeOfficetel.class)
                .getResultList();
    }
}
