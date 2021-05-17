package com.findmyself.team.data.mapcluster.repository;

import com.findmyself.team.data.domain.CenterLocation;
import com.findmyself.team.data.mapcluster.domain.ClusterMonthly;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MonthlyRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ClusterMonthly> findAll(){
        return em.createQuery("select cm from ClusterMonthly cm",ClusterMonthly.class)
                .getResultList();
    }
}
