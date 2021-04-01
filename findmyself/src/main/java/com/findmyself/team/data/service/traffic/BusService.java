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

    public TrafficBus findOne(String name){
        return busRepository.findOne(name);
    }

    public List<TrafficBus> findAll(){
        return busRepository.findAll();
    }
}
