package com.findmyself.team.data.mapcluster.repository;

import com.findmyself.team.data.mapcluster.domain.ClusterAge;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AgeRepository {
    @PersistenceContext
    private EntityManager em;

    public List<ClusterAge> findAll(){
        return em.createQuery("select ag from ClusterAge ag",ClusterAge.class)
                .getResultList();
    }
}
