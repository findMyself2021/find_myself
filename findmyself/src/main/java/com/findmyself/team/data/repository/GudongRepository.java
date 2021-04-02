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

    public List<Gudong> findAll(){
        return em.createQuery("select g from Gudong g", Gudong.class)
                .getResultList();
    }

    public Long findCodeByDong(String h_dong){ //행정동 이름으로 행정동 코드 찾기
        try {
            String jpql = "select g.h_code from Gudong g where g.h_dong = :h_dong";
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("h_dong", h_dong);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            return 0l;
        }

    }
}
