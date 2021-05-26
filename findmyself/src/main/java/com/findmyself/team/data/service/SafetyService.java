package com.findmyself.team.data.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.domain.SafetyCluster;
import com.findmyself.team.data.domain.home.HomeCluster;
import com.findmyself.team.data.repository.SafetyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SafetyService {

    private final SafetyRepository safetyRepository;

    public Safety findOne(Long h_code) {
        return safetyRepository.findOne(h_code);
    }

    public List<Safety> findAll() {
        return safetyRepository.findAll();
    }

    public int findMax() {
        return safetyRepository.findMax();
    }

    public int findMin(){ return safetyRepository.findMin(); }

    public double findRatio(Long code){
        //석차 구하기
        int rank=1;
        List<Safety> safetyList = findAll();
        for(Safety safety:safetyList){
            if (findOne(code).getValue() < safety.getValue()) {
                rank++;
            }
        }

        double ratio = Math.round(
                (((double)rank/(safetyList.size()+1))*100)*100)/100.0;
        //System.out.println("size: "+safetyList.size());
        System.out.println("ratio: "+ratio);
        //System.out.println("rank: "+rank);
        System.out.println("dd");
        //상위 퍼센트율로 반환
        return ratio;
    }

    public int findMaxNo(){
        return safetyRepository.findMaxNo();
    }

    public List<Safety> findByNo(int no){
        return safetyRepository.findByNo(no);
    }

    public List<SafetyCluster> findClusters(){
        return safetyRepository.findClusters();
    }

    public SafetyCluster findClusterByNo(int no){
        return safetyRepository.findClusterByNo(no);
    }

    //설정값을 포함하는 군집번호 찾기
    public List<Integer> findClusterNo(int safety){

        int std_value = getStdVaule(safety);

        List<SafetyCluster> clusters = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();

        clusters = safetyRepository.findClusters();

        for(SafetyCluster cluster: clusters){
            if(cluster.getMin()>=std_value){
                numbers.add(cluster.getNo());
            }
        }
        return numbers;
    }

    public List<Long> analysis(int safety){

        List<Long> codeList = new ArrayList<>();

        List<Integer> numbers;  //설정값을 충족하는 클러스터 번호
        List<Long> tmpCodes;    //특정 번호의 클러스터에 포함된 행정동 코드 리스트

        numbers = findClusterNo(safety);
        if(!numbers.isEmpty()){
            for(Integer no:numbers){
                tmpCodes = safetyRepository.findClusterByNo(no).getCodes();
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
