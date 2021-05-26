package com.findmyself.team.data.relation.service;

import com.findmyself.team.data.relation.domain.CharterTraffic;
import com.findmyself.team.data.relation.repository.CharterTrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CharterTrafficService {

    private final CharterTrafficRepository charterTrafficRepository;
    public CharterTraffic findOne(long h_code){
        return charterTrafficRepository.findOne(h_code);
    }
    public List<CharterTraffic> findAll(){
        return charterTrafficRepository.findAll();
    }
}
