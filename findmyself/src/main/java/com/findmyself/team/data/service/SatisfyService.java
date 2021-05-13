package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.Satisfy;
import com.findmyself.team.data.repository.SafetyRepository;
import com.findmyself.team.data.repository.SatisfyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SatisfyService {

    private final SatisfyRepository satisfyRepository;

    public Satisfy findOne(Long h_code) {
        return satisfyRepository.findOne(h_code);
    }
}
