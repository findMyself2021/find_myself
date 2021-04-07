package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.traffic.TrafficAddress;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AddressRepository {

    @PersistenceContext
    private EntityManager em;

    public TrafficAddress findOne(float latitude) {
        return em.find(TrafficAddress.class, latitude);
    }

    public List<TrafficAddress> findAll() {
        return em.createQuery("select tf from TrafficAddress tf", TrafficAddress.class)
                .getResultList();
    }
}
