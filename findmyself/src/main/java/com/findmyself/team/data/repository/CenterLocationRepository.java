package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.CenterLocation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CenterLocationRepository {
    @PersistenceContext
    private EntityManager em;

    public CenterLocation findOne(Long h_code){
        return  em.find(CenterLocation.class,h_code);
    }

    public List<CenterLocation> findAll(){
        return em.createQuery("select cl from CenterLocation cl",CenterLocation.class)
                .getResultList();
    }
}
