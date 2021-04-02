package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BusLocationRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TrafficBusLocation> findAll() {
        return em.createQuery("select tbl from TrafficBusLocation tbl", TrafficBusLocation.class)
                .getResultList();
    }

    public TrafficBusLocation findOneByLongitude(double longitude){
        return em.find(TrafficBusLocation.class, longitude);
    }
}
