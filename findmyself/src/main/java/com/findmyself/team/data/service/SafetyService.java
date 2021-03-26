package com.findmyself.team.data.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Safety;
import com.findmyself.team.data.repository.SafetyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SafetyService {

    private final SafetyRepository safetyRepository;

    public Safety findOne(String gu) {
        return safetyRepository.findOne(gu);
    }

    public List<Safety> findAll() {
        return safetyRepository.findAll();
    }

    public HashSet<Long> analysis(long num){
        HashSet<Long> codeList = new HashSet<>();

        List<Safety> safetyList = this.findAll();
        for(int i=0; i<safetyList.size(); i++){
            if(safetyList.get(i).getNum() <= num){
                //codeList.add(safetyList.get(i).getGu());
            }
        }

        return codeList;
    }
}
