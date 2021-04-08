package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Convenient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ConvenientRepository {
    @PersistenceContext
    private EntityManager em;

    public Convenient findOne(Long h_code){
        return em.find(Convenient.class, h_code);
    }

    public List<Convenient> findAll(){
        return em.createQuery("select c from Convenient c", Convenient.class)
                .getResultList();
    }

    public int findMax(String kind){
        return em.createQuery("select max(c."+kind+") from Convenient c", Integer.class)
                .getSingleResult();
    }

    public int findAvg(){
        int sum = 0;
        sum += em.createQuery("select max(c.joy) from Convenient c", Integer.class)
                .getSingleResult();
        sum += em.createQuery("select max(c.shop) from Convenient c", Integer.class)
                .getSingleResult();
        sum += em.createQuery("select max(c.food) from Convenient c", Integer.class)
                .getSingleResult();
        sum += em.createQuery("select max(c.life) from Convenient c", Integer.class)
                .getSingleResult();
        sum += em.createQuery("select max(c.sport) from Convenient c", Integer.class)
                .getSingleResult();
        sum += em.createQuery("select max(c.edu) from Convenient c", Integer.class)
                .getSingleResult();

        return Math.round(sum/6);
    }

}
