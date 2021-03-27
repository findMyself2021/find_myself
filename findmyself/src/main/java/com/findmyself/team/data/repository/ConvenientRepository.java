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

}
