package com.findmyself.team.data.service;


import com.findmyself.team.data.domain.AllByCharter;
import com.findmyself.team.data.domain.AllByMonthly;
import com.findmyself.team.data.repository.AllByMonthlyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllByMonthlyService {

    private final AllByMonthlyRepository allByMonthlyRepository;

    public AllByMonthly findOne(Long h_code) {
        return allByMonthlyRepository.findOne(h_code);
    }

    public List<AllByMonthly> findAll() {
        return allByMonthlyRepository.findAll();
    }

    public List<AllByMonthly> findByNo(int no){
        return allByMonthlyRepository.findByNo(no);
    }
}
