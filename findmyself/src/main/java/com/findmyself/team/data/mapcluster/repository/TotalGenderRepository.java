package com.findmyself.team.data.mapcluster.repository;

import com.findmyself.team.data.mapcluster.domain.ClusterCharter;
import com.findmyself.team.data.mapcluster.domain.ClusterGender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TotalGenderRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ClusterGender> findAll() {
        return em.createQuery("select cm from ClusterGender cm", ClusterGender.class)
                .getResultList();
    }
}
