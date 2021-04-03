package com.findmyself.team.data.service.traffic;


import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import com.findmyself.team.data.domain.traffic.TrafficSubwayLocation;
import com.findmyself.team.data.repository.traffic.BusLocationRepository;
import com.findmyself.team.data.repository.traffic.SubwayLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubwayLocationService {

    private final SubwayLocationRepository subwayLocationRepository;

    public List<TrafficSubwayLocation> findAll(){
        return subwayLocationRepository.findAll();
    }

    @Transactional
    public void updateCodeByLongitude(double longitude, Long h_code){ //경도로 행정동 코드 업뎃
        TrafficSubwayLocation subway = subwayLocationRepository.findOneByLongitude(longitude);
        subway.setH_code(h_code);
    }
}
