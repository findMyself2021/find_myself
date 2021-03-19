package com.findmyself.team.data.repository.Safety;

import com.findmyself.team.data.domain.SafetyCctv;
import com.findmyself.team.data.domain.TrafficAddress;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CctvRepository {

    @PersistenceContext
    private EntityManager em;

    public SafetyCctv findOne(String gu) {
        return em.find(SafetyCctv.class, gu);
    }

    public List<SafetyCctv> findAll() {
        return em.createQuery("select sc from SafetyCctv sc", SafetyCctv.class)
                .getResultList();
    }
}
