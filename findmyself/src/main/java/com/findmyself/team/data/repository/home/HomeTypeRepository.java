package com.findmyself.team.data.repository.home;

import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.domain.home.DasedeCharter;
import com.findmyself.team.data.domain.home.HomeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class HomeTypeRepository {
    private final EntityManager em;

    public HomeType findOne(Long h_code){
        return em.find(HomeType.class, h_code);
    }
}
