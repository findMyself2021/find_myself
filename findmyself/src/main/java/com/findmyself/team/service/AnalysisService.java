package com.findmyself.team.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.service.ConvenientService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.home.HomeService;
import com.findmyself.team.data.service.residence.AgeService;
import com.findmyself.team.data.service.residence.GenderService;
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
    ConvenientService convenientService;
    @Autowired
    GenderService genderService;
    @Autowired
    AgeService ageService;

    @Autowired
    GudongService gudongService;

    public List<Long> analysis(Requirements rq){
        List<Long> result = new ArrayList<>();
        HashSet<Long> codeList = new HashSet<>();

        HashSet<Long> homeList = homeService.analysis(rq);
        HashSet<Long> convenientList = convenientService.analysis(rq.getConvenient());
        HashSet<Long> genderList = genderService.analysis(rq.getSex_ratio());
        HashSet<Long> ageList = ageService.analysis(rq.getAge_type());

        //중복된 행정동 정리
        Iterator<Long> it_h = homeList.iterator();
        while (it_h.hasNext()) {
            codeList.add(it_h.next());
        }
        Iterator<Long> it_c = convenientList.iterator();
        while (it_c.hasNext()) {
            codeList.add(it_c.next());
        }
        Iterator<Long> it_g = genderList.iterator();
        while (it_g.hasNext()) {
            codeList.add(it_g.next());
        }
        Iterator<Long> it_a = ageList.iterator();
        while (it_a.hasNext()) {
            codeList.add(it_a.next());
        }

        Iterator<Long> it_code = codeList.iterator();
        while (it_code.hasNext()) {
            result.add(it_code.next());
        }

        // 안전 요소, 교통요소(길찾기 처리 필요) -> 구별로 나뉨 -> 마지막에 분류해주기
        return result;
    }
}
