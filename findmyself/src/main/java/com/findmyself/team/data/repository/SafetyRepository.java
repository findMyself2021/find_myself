package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Safety;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SafetyRepository {

    @PersistenceContext
    private EntityManager em;

    public Safety findOne(String gu) {
        return em.find(Safety.class, gu);
    }

    public List<Safety> findAll() {
        return em.createQuery("select s from Safety s", Safety.class)
                .getResultList();
    }
}
