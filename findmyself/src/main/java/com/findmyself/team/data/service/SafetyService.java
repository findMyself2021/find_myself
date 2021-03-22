package com.findmyself.team.data.service;

import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.repository.SafetyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SafetyService {

    private final SafetyRepository safetyRepository;

    public Safety findOne(String gu) {
        return safetyRepository.findOne(gu);
    }

    public List<Safety> findAll() {
        return safetyRepository.findAll();
    }
}
