package com.findmyself.team.data.service.traffic;

import com.findmyself.team.ConvenientTmp;
import com.findmyself.team.data.domain.Convenient;
import com.findmyself.team.data.domain.traffic.TrafficBusLocation;
import com.findmyself.team.data.domain.traffic.TrafficInfoResult;
import com.findmyself.team.data.repository.traffic.InfoResultRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InfoResultService {

    private final InfoResultRepository infoResultRepository;

    public TrafficInfoResult findOne(long h_code){
        return infoResultRepository.findOne(h_code);
    }

    public List<TrafficInfoResult> findAll(){
        return infoResultRepository.findAll();
    }

    public int findMax(){
        return infoResultRepository.findMax();
    }

    public HashSet<Long> analysis(int traffic){

        int std = Math.round(findMax()/100);

        HashSet<Long> codeList = new HashSet<>();

        List<TrafficInfoResult> trafficList = this.findAll();
        for(int i=0; i<trafficList.size(); i++){
            if(trafficList.get(i).getNum() >= std*traffic){
                codeList.add(trafficList.get(i).getH_code());
            }
        }

        return codeList;
    }
}
