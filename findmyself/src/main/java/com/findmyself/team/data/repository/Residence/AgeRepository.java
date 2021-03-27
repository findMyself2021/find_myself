package com.findmyself.team.data.repository.Residence;

import com.findmyself.team.data.domain.ResidenceAge;
import com.findmyself.team.data.domain.ResidenceGender;
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

}
