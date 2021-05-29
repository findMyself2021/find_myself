package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.AllByCharter;
import com.findmyself.team.data.domain.residence.Gender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AllByCharterRepository {

    @PersistenceContext
    private EntityManager em;

    public AllByCharter findOne(Long h_code) {
        return em.find(AllByCharter.class, h_code);
    }

    public List<AllByCharter> findAll() {
        return em.createQuery("select s from AllByCharter s", AllByCharter.class)
                .getResultList();
    }

    public List<AllByCharter> findByNo(int no){
        return em.createQuery("select g from AllByCharter g where g.no = :no", AllByCharter.class)
                .setParameter("no",no)
                .getResultList();
    }
}
