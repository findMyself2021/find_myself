package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.domain.traffic.TrafficBus;
import com.findmyself.team.data.repository.traffic.BusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusService {

    private final BusRepository busRepository;

    public List<TrafficBus> findAll(){
        return busRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void updateCodeByName(Long h_code, String name){ //정류장 이름으로 행정동 코드 업뎃
        busRepository.updateCode(h_code, name);
    }
}
