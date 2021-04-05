package com.findmyself.team.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.repository.traffic.InfoResultRepository;
import com.findmyself.team.data.service.ConvenientService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.SafetyService;
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
    SafetyService safetyService;

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
        HashSet<Long> safetyList = safetyService.analysis(rq.getSafety());
        HashSet<Long> genderList = genderService.analysis(rq.getSex_ratio());
        HashSet<Long> ageList = ageService.analysis(rq.getAge_type());

        //중복된 행정동 정리
        //각 리스트에 공통으로 포함되는 행정동만 추출  !
        //이후 일치율 높은 행정동 추천기능 수행 !
        Long code;
        int cnt=0;
        Iterator<Long> it_h = homeList.iterator();
        while (it_h.hasNext()) {
            code = it_h.next();
            if(trafficList.contains(code)){
               cnt++;
            }if(convenientList.contains(code)){
                cnt++;
            }if(safetyList.contains(code)){
                cnt++;
            }if(genderList.contains(code)){
                cnt++;
            }if(ageList.contains(code)) {
                cnt++;
            }
            if(cnt>2){ //조건 3개 이상 충족하면 추천
                codeList.add(code);
            }
        }

        return codeList;
    }
}
