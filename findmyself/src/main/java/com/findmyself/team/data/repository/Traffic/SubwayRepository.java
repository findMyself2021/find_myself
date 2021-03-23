package com.findmyself.team.data.repository.Traffic;

import com.findmyself.team.data.domain.TrafficSubway;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SubwayRepository {

    @PersistenceContext
    private EntityManager em;

    public TrafficSubway findOne(String station) {
        return em.find(TrafficSubway.class, station);
    }

    public List<TrafficSubway> findAll() {
        return em.createQuery("select ts from TrafficSubway ts", TrafficSubway.class)
                .getResultList();
    }
}