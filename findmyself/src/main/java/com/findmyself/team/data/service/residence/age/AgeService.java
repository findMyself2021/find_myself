package com.findmyself.team.data.service.residence.age;

import com.findmyself.team.data.domain.residence.age.AgeCluster;
import com.findmyself.team.data.domain.residence.age.S4050;
import com.findmyself.team.data.repository.residence.age.ChildRepository;
import com.findmyself.team.data.repository.residence.age.ElderRepository;
import com.findmyself.team.data.repository.residence.age.S2030Repository;
import com.findmyself.team.data.repository.residence.age.S4050Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AgeService {

    private final ChildService childService;
    private final S2030Service s2030Service;
    private final S4050Service s4050Service;
    private final ElderService elderService;

    public Double findOne(Long code, String age_type){
        if(age_type.equals("child")){
            return childService.findOne(code).getValue();
        }else if(age_type.equals("s2030")){
            return s2030Service.findOne(code).getValue();
        }else if(age_type.equals("s4050")){
            return s4050Service.findOne(code).getValue();
        }else {
            return elderService.findOne(code).getValue();
        }
    }

    public Double findInterval(Long code, String age_type){
        if(age_type.equals("child")){
            return Math.abs(childService.findOne(code).getValue() - childService.findMax());
        }else if(age_type.equals("s2030")){
            return Math.abs(s2030Service.findOne(code).getValue() - s2030Service.findMax());
        }else if(age_type.equals("s4050")){
            return Math.abs(s4050Service.findOne(code).getValue() - s4050Service.findMax());
        }else {
            return Math.abs(elderService.findOne(code).getValue() - elderService.findMax());
        }
    }
    //해당 연령 타입의 군집중 값이 가장큰 군집 내 행정동 반환
    public List<Long> analysis(String age_type){

        List<Long> codeList = new ArrayList<>();

        AgeCluster ageCluster;
        List<Long> Codes;    //특정 번호의 클러스터에 포함된 행정동 코드 리스트

        if(age_type.equals("child")){
            ageCluster = childService.findClusterByNo(childService.findMaxValueCluster());
        }else if(age_type.equals("s2030")){
            ageCluster = s2030Service.findClusterByNo(s2030Service.findMaxValueCluster());
        }else if(age_type.equals("s4050")){
            ageCluster = s4050Service.findClusterByNo(s4050Service.findMaxValueCluster());
        }else {
            ageCluster = elderService.findClusterByNo(elderService.findMaxValueCluster());
        }

        Codes = ageCluster.getCodes();

        for(Long code: Codes){
            codeList.add(code);
        }

        return codeList;
    }
}


