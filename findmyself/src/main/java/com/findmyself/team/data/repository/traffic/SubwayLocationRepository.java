package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import com.findmyself.team.data.domain.traffic.TrafficSubwayLocation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SubwayLocationRepository {

    @PersistenceContext
    private EntityManager em;

    public List<TrafficSubwayLocation> findAll() {
        return em.createQuery("select tsl from TrafficSubwayLocation tsl", TrafficSubwayLocation.class)
                .getResultList();
    }

    public TrafficSubwayLocation findOneByLongitude(double longitude){
        return em.find(TrafficSubwayLocation.class, longitude);
    }
}
