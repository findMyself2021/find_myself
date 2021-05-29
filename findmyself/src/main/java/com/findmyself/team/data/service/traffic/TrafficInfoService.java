package com.findmyself.team.data.service.traffic;

import com.findmyself.team.data.domain.traffic.Traffic;
import com.findmyself.team.data.repository.traffic.TrafficCluster;
import com.findmyself.team.data.repository.traffic.TrafficRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TrafficInfoService {

    private final TrafficRepository trafficRepository;

    public Traffic findOne(long h_code){
        return trafficRepository.findOne(h_code);
    }

    public List<Traffic> findAll(){
        return trafficRepository.findAll();
    }

    public double findMax(){
        return trafficRepository.findMax();
    }

    public double findMin(){
        return trafficRepository.findMin();
    }

    public int findMaxNo(){
        return trafficRepository.findMaxNo();
    }

    public List<Traffic> findByNo(int no){
        return trafficRepository.findByNo(no);
    }

    public List<TrafficCluster> findClusters(){
        return trafficRepository.findClusters();
    }

    public TrafficCluster findClusterByNo(int no){
        return trafficRepository.findClusterByNo(no);
    }

    //설정값을 포함하는 군집번호 찾기
    public List<Integer> findClusterNo(int traffic){

        int std_value = getStdVaule(traffic);

        List<TrafficCluster> clusters = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        clusters = trafficRepository.findClusters();

        for(TrafficCluster cluster: clusters){
            if(cluster.getMin()>=std_value){
                numbers.add(cluster.getNo());
            }
        }
        return numbers;
    }

    public List<Long> analysis(int traffic){

        List<Long> codeList = new ArrayList<>();

        List<Integer> numbers;  //설정값을 충족하는 클러스터 번호
        List<Long> tmpCodes;    //특정 번호의 클러스터에 포함된 행정동 코드 리스트

        numbers = findClusterNo(traffic);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = trafficRepository.findClusterByNo(no).getCodes();
                for(Long code : tmpCodes){
                    codeList.add(code);
                }
            }
        }

        return codeList;
    }

    public int getStdVaule(int safety){

        int min = (int)findMin();
        int max = (int)findMax();
        int std = Math.round((max-min)/100);
        int std_value = min+std*safety;

        return std_value;
    }
}
