package com.findmyself.team.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.repository.traffic.InfoResultRepository;
import com.findmyself.team.data.service.ConvenientService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.home.HomeService;
import com.findmyself.team.data.service.residence.AgeService;
import com.findmyself.team.data.service.residence.GenderService;
import com.findmyself.team.data.service.traffic.BusLocationService;
import com.findmyself.team.data.service.traffic.InfoResultService;
import com.findmyself.team.data.service.traffic.SubwayLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {

    @Autowired
    HomeService homeService;

    @Autowired
    InfoResultService infoResultService;

    @Autowired
    ConvenientService convenientService;

    @Autowired
    GenderService genderService;

    @Autowired
    AgeService ageService;

    @Autowired
    GudongService gudongService;

    public List<Long> analysis(Requirements rq){
        List<Long> result = new ArrayList<>();
        List<Long> codeList = new ArrayList<>();

        HashSet<Long> homeList = homeService.analysis(rq);
        HashSet<Long> trafficList = infoResultService.analysis(rq.getTraffic());
        HashSet<Long> convenientList = convenientService.analysis(rq.getConvenient());
        HashSet<Long> genderList = genderService.analysis(rq.getSex_ratio());
        HashSet<Long> ageList = ageService.analysis(rq.getAge_type());

        //중복된 행정동 정리
        //각 리스트에 공통으로 포함되는 행정동만 추출  !
        Long tmp;
        int cnt=0;
        Iterator<Long> it_h = homeList.iterator();
        while (it_h.hasNext()) {
            tmp = it_h.next();
            if(trafficList.contains(tmp)){
               cnt++;
            }if(convenientList.contains(tmp)){
                cnt++;
            }if(genderList.contains(tmp)){
                cnt++;
            }if(ageList.contains(tmp)) {
                cnt++;
            }
            if(cnt>2){
                codeList.add(tmp);
            }
        }

        for(Long tt:codeList){
            System.out.println(tt);
        }

        // 안전 요소, 교통요소(길찾기 처리 필요) -> 구별로 나뉨 -> 마지막에 분류해주기
        return codeList;
    }
}
