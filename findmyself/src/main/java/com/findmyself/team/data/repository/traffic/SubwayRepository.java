package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.TrafficSubway;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SubwayRepository {

    @PersistenceContext
    private EntityManager em;

    public TrafficSubway findOne(String name) {
        return em.find(TrafficSubway.class, name);
    }

    public List<TrafficSubway> findAll() {
        return em.createQuery("select ts from TrafficSubway ts", TrafficSubway.class)
                .getResultList();
    }
}
