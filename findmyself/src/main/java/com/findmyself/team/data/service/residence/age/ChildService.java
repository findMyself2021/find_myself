package com.findmyself.team.data.service.residence.age;

import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.Child;
import com.findmyself.team.data.repository.residence.age.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    public Child findOne(Long h_code){
        return childRepository.findOne(h_code);
    }

    public Double findMax(){
        return childRepository.findMax();
    }

    public List<Child> findAll(){
        return childRepository.findAll();
    }

    //군집 갯수
    public int findMaxNo(){
        return childRepository.findMaxNo();
    }

    public List<Child> findByNo(int no){
        return childRepository.findByNo(no);
    }

    public List<AgeCluster> findClusters(){
        return childRepository.findClusters();
    }

    public AgeCluster findClusterByNo(int no){
        return childRepository.findClusterByNo(no);
    }

    public int findMaxValueCluster(){
        List<AgeCluster> ageClusters = findClusters();
        int max = 0;
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
