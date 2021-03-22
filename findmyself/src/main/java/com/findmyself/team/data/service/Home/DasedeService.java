package com.findmyself.team.data.service.Home;

import com.findmyself.team.data.domain.HomeDandok;
import com.findmyself.team.data.domain.HomeDasede;
import com.findmyself.team.data.repository.Home.DasedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DasedeService {

    private final DasedeRepository dasedeRepository;

    public HomeDasede findOne(long h_code){
        return dasedeRepository.findOne(h_code);
    }

    public List<HomeDasede> findAll(){
        return dasedeRepository.findAll();
    }

    public List<HomeDasede> findCharters(){
        return dasedeRepository.findCharters();
    }

    public List<HomeDasede> findMonthly(){
        return dasedeRepository.findMonthly();
    }
}
