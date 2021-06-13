package com.findmyself.team.service;

import com.findmyself.team.data.relation.service.CharterTrafficService;
import com.findmyself.team.data.relation.service.GenderSafetyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelationService {

    @Autowired
    CharterTrafficService charterTrafficService;

    @Autowired
    GenderSafetyService genderSafetyService;

    public String charterTrafficRelation(Long h_code) {
        String ctrResult = "";
        int no;
        no = charterTrafficService.findOne(h_code).getNo();

        if(no == 4) {
            ctrResult = "집값이 낮으면서 교통편의도 좋지 않은 쪽에 속합니다.";
        } else if(no == 2) {
            ctrResult = "집값이 약간 낮으면서 교통편의도 약간 좋지 않은 쪽에 속합니다.";
        } else if(no == 0) {
            ctrResult = "집값이 보통이고 교통편의도 보통인 쪽에 속합니다.";
        } else if(no == 1) {
            ctrResult = "집값이 약간 높으면서 교통편의도 약간 좋은 쪽에 속합니다.";
        } else {
            ctrResult = "집값이 높고 교통편의도 좋은 쪽에 속합니다.";
        }

        return ctrResult;
    }

    public String genderSafetyRelation(Long h_code) {
        String gstResult = "";
        int no;
        no = genderSafetyService.findOne(h_code).getNo();

        if(no == 2) {
            gstResult = "남성의 비율이 높고 안전도가 낮은 쪽에 속합니다";
        } else if(no == 0) {
            gstResult = "남성의 비율이 약간 높고 안전도가 약간 낮은 쪽에 속합니다.";
        } else if(no == 4) {
            gstResult = "남녀의 비율이 비슷하고 안전도가 보통인 쪽에 속합니다.";
        } else if(no == 3) {
            gstResult = "여성의 비율이 약간 높고 안전도가 약간 높은 쪽에 속합니다.";
        } else {
            gstResult = "여성의 비율이 높고 안전도가 높은 쪽에 속합니다.";
        }

        return gstResult;
    }
}
