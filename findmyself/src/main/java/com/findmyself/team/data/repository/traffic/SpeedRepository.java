package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.traffic.TrafficSpeed;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SpeedRepository {

    @PersistenceContext
    private EntityManager em;

    // 무엇을 기준으로 찾아야 편할지 고민해봐야 함
    public TrafficSpeed findOne(String num) {
        return em.find(TrafficSpeed.class, num);
    }

    public List<TrafficSpeed> findAll() {
        return em.createQuery("select ts from TrafficSpeed ts", TrafficSpeed.class)
                .getResultList();
    }
}
