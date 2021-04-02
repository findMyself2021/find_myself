package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.HomeApart;
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

    public HomeApart findOne(long h_code){
        return apartRepository.findOne(h_code);
    }

    public List<HomeApart> findAll(){
        return apartRepository.findAll();
    }

    public List<HomeApart> findCharters(){
        return apartRepository.findCharters();
    }

    public List<HomeApart> findMonthly(){
       return apartRepository.findMonthly();
    }
}
