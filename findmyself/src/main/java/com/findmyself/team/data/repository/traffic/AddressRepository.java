package com.findmyself.team.data.repository.traffic;

import com.findmyself.team.data.domain.TrafficAddress;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AddressRepository {

    @PersistenceContext
    private EntityManager em;

    // 무엇을 기준으로 찾아야 편할지 고민해봐야 함
    public TrafficAddress findOne(String num) {
        return em.find(TrafficAddress.class, num);
    }

    public List<TrafficAddress> findAll() {
        return em.createQuery("select tf from TrafficAddress tf", TrafficAddress.class)
                .getResultList();
    }
}
