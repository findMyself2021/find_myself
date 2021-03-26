package com.findmyself.team.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Convenient;
import com.findmyself.team.data.service.ConvenientService;
import com.findmyself.team.data.service.Home.HomeService;
import com.findmyself.team.data.service.SafetyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {

    @Autowired
    HomeService homeService;
    @Autowired
    ConvenientService convenientService;
    @Autowired
    SafetyService safetyService;

    public HashSet<Long> analysis(Requirements rq){
        HashSet<Long> codeList = new HashSet<>();
        codeList = homeService.analysis(rq);
        //convenientService.analysis(rq.getConvenient());
        //safetyService.analysis(rq.getSafety());
        // 안전 요소 -> 구별로 나뉨 -> 마지막에 분류해주기
        return codeList;
    }
}
