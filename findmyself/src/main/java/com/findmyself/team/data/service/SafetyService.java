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

    public Safety findOne(Long h_code) {
        return safetyRepository.findOne(h_code);
    }

    public List<Safety> findAll() {
        return safetyRepository.findAll();
    }

    public int findMax() {
        return safetyRepository.findMax();
    }

    public HashSet<Long> analysis(int safety){  //데이터 값 낮을수록 안전이 좋은..
        int max = findMax();
        int std = Math.round(max / 100);
        HashSet<Long> codeList = new HashSet<>();

        List<Safety> safetyList = this.findAll();
        for(int i=0; i<safetyList.size(); i++){
            if(safetyList.get(i).getNum() <= (100-safety)*std){
                codeList.add(safetyList.get(i).getH_code());
            }
        }

        return codeList;
    }
}
