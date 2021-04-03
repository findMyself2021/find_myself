package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import com.findmyself.team.data.domain.traffic.TrafficInfoResult;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class InfoResultRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TrafficInfoResult> findAll() {
        return em.createQuery("select tir from TrafficInfoResult tir", TrafficInfoResult.class)
                .getResultList();
    }

    public int findMax(){
        return em.createQuery("select max(tir.num) from TrafficInfoResult tir", Integer.class)
                .getSingleResult();
    }
}
