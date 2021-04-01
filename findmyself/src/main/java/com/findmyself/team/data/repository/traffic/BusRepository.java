package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.traffic.TrafficBus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BusRepository {

    @PersistenceContext
    private EntityManager em;

    public TrafficBus findOne(String name) {
        return em.find(TrafficBus.class, name);
    }

    public List<TrafficBus> findAll() {
        return em.createQuery("select tb from TrafficBus tb", TrafficBus.class)
                .getResultList();
    }
}