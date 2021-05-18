package com.findmyself.team.data.mapcluster.service;

import com.findmyself.team.data.mapcluster.domain.ClusterConvenient;
import com.findmyself.team.data.mapcluster.domain.ClusterMonthly;
import com.findmyself.team.data.mapcluster.repository.ConvenientRepository;
import com.findmyself.team.data.mapcluster.repository.MonthlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TotalConvenientService {
    private final ConvenientRepository convenientRepository;

    public List<ClusterConvenient> findAll() {
        return convenientRepository.findAll();
    }
}
