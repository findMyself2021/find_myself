package com.findmyself.team.data.service.residence;

import com.findmyself.team.data.domain.residence.ResidenceAge;
import com.findmyself.team.data.repository.residence.AgeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class AgeService {

    private final AgeRepository ageRepository;

    public AgeService(AgeRepository ageRepository) {
        this.ageRepository = ageRepository;
    }

    public ResidenceAge findOne(long h_code){
        return ageRepository.findOne(h_code);
    }

    public List<ResidenceAge> findAll(){
        return ageRepository.findAll();
    }

    public String findFirst(ResidenceAge ra){ //1순위 연령대 칼럼명 반환

        if(ra.getFirst() == ra.getChild()){
            return "child";
        }else if(ra.getFirst() == ra.getS2030()){
            return "s2030";
        }else if(ra.getFirst() == ra.getS4050()){
            return "s4050";
        }else{
            return "elder";
        }
    }

    public String findSecond(ResidenceAge ra){ //2순위 연령대 칼럼명 반환

        if(ra.getSecond() == ra.getChild()){
            return "child";
        }else if(ra.getSecond() == ra.getS2030()){
            return "s2030";
        }else if(ra.getSecond() == ra.getS4050()){
            return "s4050";
        }else{
            return "elder";
        }
    }

    public HashSet<Long> analysis(String age_type){

        HashSet<Long> codeList = new HashSet<>();
        List<ResidenceAge> ageList = findAll();

        for(int i=0; i<ageList.size(); i++){
            if(findFirst(ageList.get(i)).equals(age_type)
                || findSecond(ageList.get(i)).equals(age_type)){
                codeList.add(ageList.get(i).getH_code());
            }
        }
        return codeList;
    }
}


