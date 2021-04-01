package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.domain.traffic.TrafficSubway;
import com.findmyself.team.data.repository.traffic.SubwayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubwayService {

    private final SubwayRepository subwayRepository;

    public TrafficSubway findOne(String station) {
        return subwayRepository.findOne(station);
    }

    public List<TrafficSubway> findAll() {
        return subwayRepository.findAll();
    }
}
