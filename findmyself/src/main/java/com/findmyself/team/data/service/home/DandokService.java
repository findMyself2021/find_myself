package com.findmyself.team.data.service.home;

import com.findmyself.team.data.domain.home.HomeDandok;
import com.findmyself.team.data.repository.home.DandokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DandokService {

    private final DandokRepository dandokRepository;

    public HomeDandok findOne(long h_code){
        return dandokRepository.findOne(h_code);
    }

    public List<HomeDandok> findAll(){
        return dandokRepository.findAll();
    }

    public List<HomeDandok> findCharters(){
        return dandokRepository.findCharters();
    }

    public List<HomeDandok> findMonthly(){
        return dandokRepository.findMonthly();
    }
}
