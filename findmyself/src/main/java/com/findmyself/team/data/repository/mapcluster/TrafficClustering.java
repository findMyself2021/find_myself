package com.findmyself.team.data.repository.mapcluster;

import com.findmyself.team.data.domain.traffic.Traffic;
import com.findmyself.team.data.service.traffic.TrafficInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class TrafficClustering {
    @Autowired
    TrafficInfoService trafficInfoService;

    public List<TrafficLite> clusterTraffic(){
        List<TrafficLite> list = new ArrayList<>();
        List<Traffic> trafficList = trafficInfoService.findAll();

        for(int i=0;i<trafficList.size();i++){
            Long h_code = trafficList.get(i).getH_code();
            int no = trafficList.get(i).getNo();

//            System.out.println("h_code = " + h_code + "no = " +no);

            TrafficLite trafficLite = new TrafficLite(h_code,no);
            list.add(trafficLite);
        }

        return list;
    }
}
