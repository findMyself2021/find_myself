package com.findmyself.team.data.relation.repository;

import com.findmyself.team.data.relation.domain.GenderSafety;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenderSafetyRepository {
    @PersistenceContext
    private EntityManager em;
    public List<GenderSafety> findAll(){
        return em.createQuery("select gs from GenderSafety gs", GenderSafety.class)
                .getResultList();
    }

    public GenderSafety findOne(Long h_code){
        return em.find(GenderSafety.class,h_code);
    }
}
