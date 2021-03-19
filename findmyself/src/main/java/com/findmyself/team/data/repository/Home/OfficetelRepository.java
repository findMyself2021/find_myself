package com.findmyself.team.data.repository.Home;

import com.findmyself.team.data.domain.HomeApart;
import com.findmyself.team.data.domain.HomeOfficetel;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OfficetelRepository {
    @PersistenceContext
    private EntityManager em;

    public HomeOfficetel findOne(Long h_code){
        return em.find(HomeOfficetel.class,h_code);
    }

    public List<HomeOfficetel> findAll(){
        return em.createQuery("select ho from HomeOfficetel ho",HomeOfficetel.class)
                .getResultList();
    }
}
