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

    public Safety findOne(Long h_code) {
        return em.find(Safety.class, h_code);
    }

    public List<Safety> findAll() {
        return em.createQuery("select s from Safety s", Safety.class)
                .getResultList();
    }

    public int findMax(){
        return em.createQuery("select max(s.num) from Safety s", Integer.class)
                .getSingleResult();
    }

    public int findMin(){
        return em.createQuery("select min(s.num) from Safety s", Integer.class)
                .getSingleResult();
    }
}
