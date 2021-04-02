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

    public List<TrafficBus> findAll() {
        return em.createQuery("select tb from TrafficBus tb", TrafficBus.class)
                .getResultList();
    }

    public void updateCode(Long h_code, String name){
        em.createQuery("update TrafficBus tb set tb.h_code = :h_code where tb.name = :name");
    }
}