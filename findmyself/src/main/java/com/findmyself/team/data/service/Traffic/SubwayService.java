package com.findmyself.team.data.service.Traffic;

import com.findmyself.team.data.domain.TrafficSubway;
import com.findmyself.team.data.repository.Traffic.SubwayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubwayService {

    private final SubwayRepository subwayRepository;

    public TrafficSubway findOne(String name) {
        return subwayRepository.findOne(name);
    }

    public List<TrafficSubway> findAll() {
        return subwayRepository.findAll();
    }
}
