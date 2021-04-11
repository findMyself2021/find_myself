package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.home.HomeApart;
import com.findmyself.team.data.domain.home.HomeDandok;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApartRepository {

    private final EntityManager em;

    public HomeApart findOne(Long h_code){
        return em.createQuery("select ha from HomeApart ha where ha.h_code = :h_code", HomeApart.class)
                .setParameter("h_code",h_code)
                .getSingleResult();
    }

    public List<HomeApart> findAll(){
        return em.createQuery("select ha from HomeApart ha",HomeApart.class)
                .getResultList();
    }
}
