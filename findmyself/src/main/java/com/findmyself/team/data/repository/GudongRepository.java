package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Convenient;
import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.service.GudongService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GudongRepository {

    @PersistenceContext
    private EntityManager em;

    public Gudong findOne(Long h_code){
        return em.find(Gudong.class, h_code);
    }

    public List<Gudong> findAll() {
        return em.createQuery("select g from Gudong g", Gudong.class)
                .getResultList();
    }
}
