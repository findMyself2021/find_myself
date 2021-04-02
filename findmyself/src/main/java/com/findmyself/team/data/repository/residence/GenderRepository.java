package com.findmyself.team.data.repository.residence;

import com.findmyself.team.data.domain.residence.ResidenceGender;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class GenderRepository {
    @PersistenceContext
    private EntityManager em;

    public ResidenceGender findOne(Long h_code){
        return em.find(ResidenceGender.class,h_code);
    }

    public List<ResidenceGender> findAll(){
        return em.createQuery("select rg from ResidenceGender rg",ResidenceGender.class)
                .getResultList();
    }

    public double findMax(){
        return em.createQuery("select max(rg.sex_ratio) from ResidenceGender rg", Double.class)
                .getSingleResult();
    }

    public double findMin(){
        return em.createQuery("select min(rg.sex_ratio) from ResidenceGender rg", Double.class)
                .getSingleResult();
    }

}