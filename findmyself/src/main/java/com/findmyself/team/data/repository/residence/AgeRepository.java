package com.findmyself.team.data.repository.residence;

import com.findmyself.team.data.domain.residence.ResidenceAge;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AgeRepository {
    @PersistenceContext
    private EntityManager em;

    public ResidenceAge findOne(Long h_code){
        return em.find(ResidenceAge.class,h_code);
    }

    public List<ResidenceAge> findAll(){
        return em.createQuery("select ra from ResidenceAge ra",ResidenceAge.class)
                .getResultList();
    }

    public int findMax(String type){
        return em.createQuery("select max(c."+type+") from Convenient c", Integer.class)
                .getSingleResult();
    }

}
