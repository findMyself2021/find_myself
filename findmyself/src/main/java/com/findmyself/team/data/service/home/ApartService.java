package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.HomeApart;
import com.findmyself.team.data.repository.home.ApartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ApartService {

    private final ApartRepository apartRepository;

    public HomeApart findOne(Long h_code){
        return apartRepository.findOne(h_code);
    }

    public List<HomeApart> findAll(){
        return apartRepository.findAll();
    }

}
