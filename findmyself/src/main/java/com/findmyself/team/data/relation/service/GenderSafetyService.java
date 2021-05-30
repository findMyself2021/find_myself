package com.findmyself.team.data.relation.service;

import com.findmyself.team.data.relation.domain.CharterTraffic;
import com.findmyself.team.data.relation.domain.GenderSafety;
import com.findmyself.team.data.relation.repository.CharterTrafficRepository;
import com.findmyself.team.data.relation.repository.GenderSafetyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenderSafetyService {
    private final GenderSafetyRepository genderSafetyRepository;
    public GenderSafety findOne(long h_code){
        return genderSafetyRepository.findOne(h_code);
    }
    public List<GenderSafety> findAll(){
        return genderSafetyRepository.findAll();
    }
}
