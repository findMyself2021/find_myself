package com.findmyself.team.data.service.Safety;

import com.findmyself.team.data.domain.SafetyCrime;
import com.findmyself.team.data.repository.Safety.CrimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CrimeService {

    private final CrimeRepository crimeRepository;

    public SafetyCrime findOne(String gu) {
        return crimeRepository.findOne(gu);
    }

    public List<SafetyCrime> findAll() {
        return crimeRepository.findAll();
    }
}
