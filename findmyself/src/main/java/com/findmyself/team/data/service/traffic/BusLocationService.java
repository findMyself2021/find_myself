package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import com.findmyself.team.data.repository.traffic.BusLocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusLocationService {

    private final BusLocationRepository busLocationRepository;

    public List<TrafficBusLocation> findAll(){
        return busLocationRepository.findAll();
    }

    @Transactional
    public void updateCodeByLongitude(double longitude, Long h_code){ //경도로 행정동 코드 업뎃
        TrafficBusLocation bus = busLocationRepository.findOneByLongitude(longitude);
        bus.setH_code(h_code);
    }
}
