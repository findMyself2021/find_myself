package com.findmyself.team.data.mapcluster.service;

import com.findmyself.team.data.mapcluster.domain.ClusterCharter;
import com.findmyself.team.data.mapcluster.domain.ClusterMonthly;
import com.findmyself.team.data.mapcluster.repository.CharterRepository;
import com.findmyself.team.data.mapcluster.repository.MonthlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CharterService {
    private final CharterRepository charterRepository;

    public List<ClusterCharter> findAll() {
        return charterRepository.findAll();
    }
}
