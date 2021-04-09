package com.findmyself.team.service;

import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Convenient;
import com.findmyself.team.data.service.ConvenientService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.SafetyService;
import com.findmyself.team.data.service.home.HomeService;
import com.findmyself.team.data.service.residence.AgeService;
import com.findmyself.team.data.service.residence.GenderService;
import com.findmyself.team.data.service.traffic.InfoResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

        HashSet<Long> homeList = homeService.analysis(rq.getHome_type(), rq.getDeposit(), rq.getMonthly());
        HashSet<Long> trafficList = infoResultService.analysis(rq.getTraffic());
        HashSet<Long> convenientList = convenientService.analysis(rq.getConvenient());
        HashSet<Long> safetyList = safetyService.analysis(rq.getSafety());
        HashSet<Long> genderList = genderService.analysis(rq.getSex_ratio());
        HashSet<Long> ageList = ageService.analysis(rq.getAge_type());

        /*중복된 행정동 정리
        각 리스트에 공통으로 포함되는 행정동만 추출  !
        후 일치율 높은 행정동 추천기능 수행 !*/
        Long code;
        int cnt=0;
        Iterator<Long> it_h = homeList.iterator();  //예산은 항상 충족하도록
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

    public List<Long> findMatchingTop5(Requirements rq, List<Long> codeList){

        List<Long> topCodeList = new ArrayList<>();

        Map<Integer, Long> intervalList = new HashMap<Integer, Long>();

        int interval = 0;

        // 추출한 추천 행정동 리스트에서 매칭률 높은곳 찾기
        // 인터벌 값이 작을수록 좋음
        for(Long code_tmp: codeList){

            System.out.println("행정동 코드: "+ code_tmp);
            // 전월세 설정값에 가까운
            if(rq.getHome_type().equals("charter")){    //전세
                interval += homeService.findDepositByAvg(code_tmp) - rq.getDeposit();
            }else{  //월세
                interval += homeService.findDepositByAvg(code_tmp) - rq.getDeposit();
                interval += homeService.findMonthlyByAvg(code_tmp) - rq.getMonthly();
            }

            // 교통량 최댓값에 가까운
            interval += infoResultService.findMax()
                    - infoResultService.findOne(code_tmp).getNum();

            // 편의 수치 최댓값에 가까운
            interval += findConvenientInterval(code_tmp);

            //안전 수치 최솟값에 가까운
            interval += Math.abs(safetyService.findMin() - safetyService.findOne(code_tmp).getNum());

            //성비
            String preferGender = genderService.findPrefer(
                    genderService.getStdVaule(rq.getSex_ratio()),
                    genderService.getMidValue()
            );

            if(preferGender.equals("w")){  //여초 선호
                interval += (int)(genderService.findOne(code_tmp).getSex_ratio()
                        - genderService.findMin());
            }else if(preferGender.equals("m")){    //남초 선호
                interval += (int)(genderService.findOne(code_tmp).getSex_ratio()
                        - genderService.findMax());
            }else{  //반반
                interval += Math.abs((int)(genderService.findOne(code_tmp).getSex_ratio()
                        - genderService.getMidValue()));
            }

            //선택 연령대 인구수의 최대값에 가까운
            String age_type = rq.getAge_type();
            if(age_type.equals("child")){
                interval += ageService.findOne(code_tmp).getChild()- ageService.findMax(age_type);
            }else if(age_type.equals("s2030")){
                interval += ageService.findOne(code_tmp).getS2030()- ageService.findMax(age_type);
            }else if(age_type.equals("s4050")){
                interval += ageService.findOne(code_tmp).getS4050() - ageService.findMax(age_type);
            }else{
                interval += ageService.findOne(code_tmp).getElder()- ageService.findMax(age_type);
            }

            intervalList.put(interval, code_tmp);
        }

        //인터벌값 오름차순 정렬 top5
        topCodeList = sortIntervalList(intervalList);

        return topCodeList;
    }

    public int findConvenientInterval(Long code){
        int interval = 0;

        Convenient conv = convenientService.findOne(code);

        interval += convenientService.findMax("joy") - conv.getJoy();
        interval += convenientService.findMax("shop") - conv.getShop();
        interval += convenientService.findMax("food") - conv.getFood();
        interval += convenientService.findMax("life") - conv.getLife();
        interval += convenientService.findMax("sport") - conv.getSport();
        interval += convenientService.findMax("edu") - conv.getEdu();

        return Math.round(interval/6);
    }

    public List<Long> sortIntervalList(Map<Integer, Long> intervalList){    //인터벌값 오름차순 정렬 top5

        List<Long> topCodeList = new ArrayList<>();

        List<Integer> keys = new ArrayList<>(intervalList.keySet());
        Collections.sort(keys);
        keys.sort(Collections.reverseOrder());

        // 결과 출력
        for (int i=0; i<5; i++)
        {
            int itv = keys.get(i);
            Long h_code = intervalList.get(keys.get(i));
            String h_dong = gudongService.findOne(h_code).getH_dong();

            System.out.println("interval: "+itv+", h_dong: "+h_dong);

            //topCodeList.add(gudongService.findOne(h_code));
        }

        return topCodeList;
    }
}
