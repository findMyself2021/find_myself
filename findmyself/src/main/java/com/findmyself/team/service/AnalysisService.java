package com.findmyself.team.service;

import com.findmyself.team.AnalysisInfo;
import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Convenient;
import com.findmyself.team.data.domain.residence.ResidenceGender;
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

    // 필터링 분석
    public List<Long> analysis(Requirements rq){
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
            if(cnt>=2){ //예산 외 조건 2개 이상 충족하면 추천
                codeList.add(code);
            }
        }

        return codeList;
    }

    // 매칭률 top5 분석
    // (위 분석에서)필터링 된 리스트 이용
    public List<DongInfo> findMatchingTop5(Requirements rq, List<Long> codeList){

        List<DongInfo> topInfoList;

        Map<Integer, Long> intervalList = new HashMap<>();

        int interval = 0;

        // 추출한 추천 행정동 리스트에서 매칭률 높은곳 찾기
        // 인터벌 값이 작을수록 좋음
        for(Long code_tmp: codeList){
            //System.out.println("행정동 코드: "+ code_tmp);

            // 전월세 설정값에 가까운
            if(rq.getHome_type().equals("charter")){    //전세 필터링 경우
                interval += rq.getDeposit() - homeService.findDepositAvgByCharter(code_tmp);
            }else{  //월세 필터링 경우
                interval += rq.getDeposit() - homeService.findDepositAvgByMonthly(code_tmp);
                interval += rq.getMonthly() - homeService.findMonthlyAvgByMonthly(code_tmp);
            }

            // 교통량 최댓값에 가까운
            interval += Math.abs(infoResultService.findMax()
                    - infoResultService.findOne(code_tmp).getNum());

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
                interval += Math.abs((int)(genderService.findOne(code_tmp).getSex_ratio()
                        - genderService.findMin()));
            }else if(preferGender.equals("m")){    //남초 선호
                interval += Math.abs((int)(genderService.findOne(code_tmp).getSex_ratio()
                        - genderService.findMax()));
            }else{  //반반
                interval += Math.abs((int)(genderService.findOne(code_tmp).getSex_ratio()
                        - genderService.getMidValue()));
            }

            //선택 연령대 인구수의 최대값에 가까운
            String age_type = rq.getAge_type();
            if(age_type.equals("child")){
                interval += Math.abs(ageService.findOne(code_tmp).getChild()- ageService.findMax(age_type));
            }else if(age_type.equals("s2030")){
                interval += Math.abs(ageService.findOne(code_tmp).getS2030()- ageService.findMax(age_type));
            }else if(age_type.equals("s4050")){
                interval += Math.abs(ageService.findOne(code_tmp).getS4050() - ageService.findMax(age_type));
            }else{
                interval += Math.abs(ageService.findOne(code_tmp).getElder()- ageService.findMax(age_type));
            }

            intervalList.put(interval, code_tmp);
        }

        //인터벌값 오름차순 정렬 top5
        topInfoList = sortIntervalList(intervalList);

        return topInfoList;
    }

    // 편의시설 인터벌값 계산
    public int findConvenientInterval(Long code){
        int interval = 0;

        Convenient conv = convenientService.findOne(code);

        interval += Math.abs(convenientService.findMax("joy") - conv.getJoy());
        interval += Math.abs(convenientService.findMax("shop") - conv.getShop());
        interval += Math.abs(convenientService.findMax("food") - conv.getFood());
        interval += Math.abs(convenientService.findMax("life") - conv.getLife());
        interval += Math.abs(convenientService.findMax("sport") - conv.getSport());
        interval += Math.abs(convenientService.findMax("edu") - conv.getEdu());

        return Math.round(interval/6);
    }

    //인터벌값 오름차순 정렬 top5
    public List<DongInfo> sortIntervalList(Map<Integer, Long> intervalList){

        List<DongInfo> topInfoList = new ArrayList<>();

        List<Integer> keys = new ArrayList<>(intervalList.keySet());
        Collections.sort(keys);

        // 결과 출력
        for (int i=0; i<20; i++)
        {
            int itv = keys.get(i);
            Long h_code = intervalList.get(keys.get(i));
            String gu = gudongService.findOne(h_code).getGu();
            String h_dong = gudongService.findOne(h_code).getH_dong();

            System.out.println("interval: "+itv+", h_dong: "+h_dong);

            DongInfo dongInfo = new DongInfo(gu,h_dong,h_code);
            topInfoList.add(dongInfo);
        }

        return topInfoList;
    }

    //상세분석 관련 서비스
    public AnalysisInfo analysisDetail(Long code){

        int deposit_avg_charter = homeService.findDepositAvgByCharter(code);

        int deposit_avg_monthly = homeService.findDepositAvgByMonthly(code);
        int monthly_avg_monthly = homeService.findMonthlyAvgByMonthly(code);

        // 성비 구하기
        ResidenceGender gender = genderService.findOne(code);
        int sum = gender.getMale()+gender.getFemale();
        double man_ratio = Math.round(((double) gender.getMale()/(double) sum*100)*100)/100.0;
        double woman_ratio = Math.round(((double) gender.getFemale()/(double) sum*100)*100)/100.0;
        System.out.println(man_ratio+", "+woman_ratio);

        AnalysisInfo result = new AnalysisInfo(deposit_avg_charter,deposit_avg_monthly,monthly_avg_monthly,man_ratio,woman_ratio);

        return result;
    }
}
