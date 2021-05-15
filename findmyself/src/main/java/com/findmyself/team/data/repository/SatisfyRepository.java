package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.Satisfy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SatisfyRepository {

    @PersistenceContext
    private EntityManager em;

    public Satisfy findOne(Long h_code) {
        return em.find(Satisfy.class, h_code);
    }
}
