package com.findmyself.team.data.service.Home;

import com.findmyself.team.data.domain.HomeDandok;
import com.findmyself.team.data.repository.Home.DandokRepository;
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
}
