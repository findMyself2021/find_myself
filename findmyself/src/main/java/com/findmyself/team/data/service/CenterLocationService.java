package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.CenterLocation;
import com.findmyself.team.data.repository.CenterLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CenterLocationService {

    private final CenterLocationRepository centerLocationRepository;

    public CenterLocation findOne(long h_code){
        return centerLocationRepository.findOne(h_code);
    }

    public List<CenterLocation> findAll(){
        return centerLocationRepository.findAll();
    }
}
