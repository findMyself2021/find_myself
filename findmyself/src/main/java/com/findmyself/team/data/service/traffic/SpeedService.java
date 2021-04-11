package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.domain.traffic.TrafficSpeed;
import com.findmyself.team.data.domain.traffic.TrafficVolume;
import com.findmyself.team.data.repository.traffic.SpeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SpeedService {
    private final SpeedRepository speedRepository;

    public TrafficSpeed findOne(String num) {
        return speedRepository.findOne(num);
    }

    public List<TrafficSpeed> findAll() {
        return speedRepository.findAll();
    }
}
