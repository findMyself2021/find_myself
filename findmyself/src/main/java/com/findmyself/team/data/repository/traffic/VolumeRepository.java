package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.TrafficVolume;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class VolumeRepository {

    @PersistenceContext
    private EntityManager em;

    // 무엇을 기준으로 찾아야 편할지 고민해봐야 함
    public TrafficVolume findOne(String num) {
        return em.find(TrafficVolume.class, num);
    }

    public List<TrafficVolume> findAll() {
        return em.createQuery("select tv from TrafficVolume tv", TrafficVolume.class)
                .getResultList();
    }
}
