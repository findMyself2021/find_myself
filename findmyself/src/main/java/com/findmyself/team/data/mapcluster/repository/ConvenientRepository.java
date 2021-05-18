package com.findmyself.team.data.mapcluster.repository;

import com.findmyself.team.data.mapcluster.domain.ClusterConvenient;
import com.findmyself.team.data.mapcluster.domain.ClusterMonthly;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ConvenientRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ClusterConvenient> findAll(){
        return em.createQuery("select cc from ClusterConvenient cc", ClusterConvenient.class)
                .getResultList();
    }
}
