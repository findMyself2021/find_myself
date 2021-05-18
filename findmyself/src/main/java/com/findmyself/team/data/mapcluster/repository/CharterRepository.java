package com.findmyself.team.data.mapcluster.repository;

import com.findmyself.team.data.mapcluster.domain.ClusterCharter;
import com.findmyself.team.data.mapcluster.domain.ClusterMonthly;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CharterRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ClusterCharter> findAll() {
        return em.createQuery("select cm from ClusterCharter cm", ClusterCharter.class)
                .getResultList();
    }
}
