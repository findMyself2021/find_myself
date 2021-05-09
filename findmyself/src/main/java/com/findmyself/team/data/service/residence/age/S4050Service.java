package com.findmyself.team.data.service.residence.age;

import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.Child;
import com.findmyself.team.data.domain.residence.age.Elder;
import com.findmyself.team.data.domain.residence.age.S4050;
import com.findmyself.team.data.repository.residence.age.ElderRepository;
import com.findmyself.team.data.repository.residence.age.S4050Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S4050Service {

    private final S4050Repository s4050Repository;

    public S4050 findOne(Long h_code){
        return s4050Repository.findOne(h_code);
    }

    public Double findMax(){
        return s4050Repository.findMax();
    }

    public List<S4050> findAll(){
        return s4050Repository.findAll();
    }

    //군집 갯수
    public int findMaxNo(){
        return s4050Repository.findMaxNo();
    }

    public List<S4050> findByNo(int no){
        return s4050Repository.findByNo(no);
    }

    public List<AgeCluster> findClusters(){
        return s4050Repository.findClusters();
    }

    public AgeCluster findClusterByNo(int no){
        return s4050Repository.findClusterByNo(no);
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