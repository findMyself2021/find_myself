package com.findmyself.team.data.repository.residence;

<<<<<<< HEAD:findmyself/src/main/java/com/findmyself/team/data/repository/residence/AgeRepository.java
import com.findmyself.team.data.domain.ResidenceAge;
=======
import com.findmyself.team.data.domain.residence.ResidenceAge;
>>>>>>> master:findmyself/src/main/java/com/findmyself/team/data/repository/Residence/AgeRepository.java
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
