package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.AllByCharter;
import com.findmyself.team.data.domain.AllByMonthly;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AllByMonthlyRepository {

    @PersistenceContext
    private EntityManager em;

    public AllByMonthly findOne(Long h_code) {
        return em.find(AllByMonthly.class, h_code);
    }

    public List<AllByMonthly> findAll() {
        return em.createQuery("select s from AllByMonthly s", AllByMonthly.class)
                .getResultList();
    }

    public List<AllByMonthly> findByNo(int no){
        return em.createQuery("select g from AllByMonthly g where g.no = :no", AllByMonthly.class)
                .setParameter("no",no)
                .getResultList();
    }
}
