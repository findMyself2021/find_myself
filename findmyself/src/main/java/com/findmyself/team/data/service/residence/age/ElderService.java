package com.findmyself.team.data.service.residence.age;

import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.Child;
import com.findmyself.team.data.domain.residence.age.Elder;
import com.findmyself.team.data.repository.residence.age.ChildRepository;
import com.findmyself.team.data.repository.residence.age.ElderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ElderService {

    private final ElderRepository elderRepository;

    public Elder findOne(Long h_code){
        return elderRepository.findOne(h_code);
    }

    public Double findMax(){
        return elderRepository.findMax();
    }

    public List<Elder> findAll(){
        return elderRepository.findAll();
    }

    //군집 갯수
    public int findMaxNo(){
        return elderRepository.findMaxNo();
    }

    public List<Elder> findByNo(int no){
        return elderRepository.findByNo(no);
    }

    public List<AgeCluster> findClusters(){
        return elderRepository.findClusters();
    }

    public AgeCluster findClusterByNo(int no){
        return elderRepository.findClusterByNo(no);
    }

    public int findMaxValueCluster(){
        List<AgeCluster> ageClusters = findClusters();
        double max = 0;
        int max_no = -1;  //수치가 가장 높은 클러스터 번호
        for(AgeCluster ageCluster : ageClusters){
            if(max < ageCluster.getMax()){
                max = ageCluster.getMax();
                max_no = ageCluster.getNo();
            }
        }
        if(max_no == -1){
            System.out.println("top 클러스터 번호 추출 오류 발생.");
        }

        return max_no;
    }
}