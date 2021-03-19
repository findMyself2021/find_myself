package com.findmyself.team.data.repository.Safety;

import com.findmyself.team.data.domain.SafetyCctv;
import com.findmyself.team.data.domain.SafetyCrime;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CrimeRepository {

    @PersistenceContext
    private EntityManager em;

    public SafetyCrime findOne(String gu) {
        return em.find(SafetyCrime.class, gu);
    }

    public List<SafetyCrime> findAll() {
        return em.createQuery("select sc from SafetyCrime sc", SafetyCrime.class)
                .getResultList();
    }
}
