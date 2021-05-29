package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.AllByCharter;
import com.findmyself.team.data.repository.AllByCharterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllByCharterService {

    private final AllByCharterRepository allByCharterRepository;

    public AllByCharter findOne(Long h_code) {
        return allByCharterRepository.findOne(h_code);
    }

    public List<AllByCharter> findAll() {
        return allByCharterRepository.findAll();
    }

    public List<AllByCharter> findByNo(int no){
        return allByCharterRepository.findByNo(no);
    }
}
