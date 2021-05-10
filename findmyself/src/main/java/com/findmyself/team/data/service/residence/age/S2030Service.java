package com.findmyself.team.data.service.residence.age;

import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.Child;
import com.findmyself.team.data.domain.residence.age.Elder;
import com.findmyself.team.data.domain.residence.age.S2030;
import com.findmyself.team.data.repository.residence.age.ElderRepository;
import com.findmyself.team.data.repository.residence.age.S2030Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S2030Service {

    private final S2030Repository s2030Repository;

    public S2030 findOne(Long h_code){
        return s2030Repository.findOne(h_code);
    }

    public Double findMax(){
        return s2030Repository.findMax();
    }

    public List<S2030> findAll(){
        return s2030Repository.findAll();
    }

    //군집 갯수
    public int findMaxNo(){
        return s2030Repository.findMaxNo();
    }

    public List<S2030> findByNo(int no){
        return s2030Repository.findByNo(no);
    }

    public List<AgeCluster> findClusters(){
        return s2030Repository.findClusters();
    }

    public AgeCluster findClusterByNo(int no){
        return s2030Repository.findClusterByNo(no);
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