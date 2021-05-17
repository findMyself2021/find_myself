package com.findmyself.team.data.mapcluster.service;

import com.findmyself.team.data.domain.convenient.Joy;
import com.findmyself.team.data.mapcluster.domain.ClusterMonthly;
import com.findmyself.team.data.mapcluster.repository.MonthlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MonthlyService {

    private final MonthlyRepository monthlyRepository;

    public List<ClusterMonthly> findAll() {
        return monthlyRepository.findAll();
    }
}
