package com.findmyself.team.data.relation.repository;

import com.findmyself.team.data.mapcluster.domain.ClusterCharter;
import com.findmyself.team.data.relation.domain.CharterTraffic;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CharterTrafficRepository {
    @PersistenceContext
    private EntityManager em;
    public List<CharterTraffic> findAll(){
        return em.createQuery("select ct from CharterTraffic ct", CharterTraffic.class)
                .getResultList();
    }

    public CharterTraffic findOne(Long h_code){
        return em.find(CharterTraffic.class,h_code);
    }
}
