package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.domain.traffic.TrafficBus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusService {

    private final BusService busService;

    public TrafficBus findOne(String name){
        return busService.findOne(name);
    }

    public List<TrafficBus> findAll(){
        return busService.findAll();
    }
}
