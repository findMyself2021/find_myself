package com.findmyself.team.data.mapcluster.service;

import com.findmyself.team.data.mapcluster.domain.ClusterGender;
import com.findmyself.team.data.mapcluster.repository.TotalGenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TotalGenderService {
    private final TotalGenderRepository totalGenderRepository;
    public List<ClusterGender> findAll(){
        return totalGenderRepository.findAll();
    }
}
