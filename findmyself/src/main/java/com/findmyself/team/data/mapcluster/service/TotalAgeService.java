package com.findmyself.team.data.mapcluster.service;

import com.findmyself.team.data.mapcluster.domain.ClusterAge;
import com.findmyself.team.data.mapcluster.repository.AgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TotalAgeService {
    private final AgeRepository ageRepository;

    public List<ClusterAge> findAll(){
        return ageRepository.findAll();
    }
}
