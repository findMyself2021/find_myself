package com.findmyself.team.service;

import com.findmyself.team.AnalysisInfo;
import com.findmyself.team.DongInfo;
import com.findmyself.team.TrafficInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.domain.home.HomeType;
import com.findmyself.team.data.domain.residence.Gender;
import com.findmyself.team.data.service.SatisfyService;
import com.findmyself.team.data.service.convenient.ConvenientService;
import com.findmyself.team.data.service.GudongService;
import com.findmyself.team.data.service.SafetyService;
import com.findmyself.team.data.service.home.HomeService;
import com.findmyself.team.data.service.home.HomeTypeService;
import com.findmyself.team.data.service.residence.age.AgeService;
import com.findmyself.team.data.service.residence.GenderService;
import com.findmyself.team.data.service.traffic.TrafficInfoService;
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
    HomeTypeService homeTypeService;

    @Autowired
    TrafficInfoService trafficInfoService;

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

    @Autowired
    MemberService memberService;

    @Autowired
    SatisfyService satisfyService;

    // 필터링 분석
    public List<Long> analysis(Requirements rq){
        List<Long> codeList = new ArrayList<>();

        HashSet<Long> homeList = homeService.analysis(rq.getHome_type(), rq.getDeposit(), rq.getMonthly());
//        System.out.println("<<<homeList 분석결과>>>");
//        for(Long code: homeList){
//            System.out.println(code);
//        }

        List<Long> trafficList = trafficInfoService.analysis(rq.getTraffic());
//        System.out.println("<<<trafficList 분석결과>>>");
//        for(Long code: trafficList){
//            System.out.println(code);
//        }

        List<Long> convenientList = convenientService.analysis(rq.getConvenient());
//        System.out.println("<<<convenientList 분석결과>>>");
//        for(Long code: convenientList){
//            System.out.println(code);
//        }

        List<Long> safetyList = safetyService.analysis(rq.getSafety());
//        System.out.println("<<<safetyList 분석결과>>>");
//        for(Long code: safetyList){
//            System.out.println(code);
//        }

        List<Long> genderList = genderService.analysis(rq.getSex_ratio());
//        System.out.println("<<<genderList 분석결과>>>");
//        for(Long code: genderList){
//            System.out.println(code);
//        }

        List<Long> ageList = ageService.analysis(rq.getAge_type());
//        System.out.println("<<<ageList 분석결과>>>");
//        for(Long code: ageList){
//            System.out.println(code);
//        }


        /*중복된 행정동 정리
        각 리스트에 공통으로 포함되는 행정동만 추출  !
        후 일치율 높은 행정동 추천기능 수행 !*/
        Long code;
        int cnt;

        if(homeList.size() == 0){   //예산 충족 리스트가 아예 없는 경우
            for(Gudong gudong:gudongService.findAll()){
                cnt=0;
                code = gudong.getH_code();
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
        }else{
            Iterator<Long> it_h = homeList.iterator();  //예산은 항상 충족하도록
            while (it_h.hasNext()) {
                cnt=0;
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
        }

        System.out.println("<<<<<최종 추천 행정동 리스트>>>>>");
        System.out.println(codeList);
        return codeList;

    }

    // top 매칭률 분석
    // (위 분석에서)필터링 된 리스트 이용
    public List<DongInfo> findMatchingTop(Requirements rq, List<Long> codeList){

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

            // 교통량 설정값에 가까운
            interval += Math.abs(trafficInfoService.getStdVaule(rq.getTraffic())
                    - trafficInfoService.findOne(code_tmp).getValue());

            //편의 수치 설정값에 가까운
            interval += convenientService.findInterval(rq, code_tmp);

            //안전 수치 설정값에 가까운
            interval += Math.abs(safetyService.getStdVaule(rq.getSafety()) - safetyService.findOne(code_tmp).getValue());

            //성비
            String preferGender = genderService.findPrefer(
                    genderService.getStdVaule(rq.getSex_ratio())
            );

            if(preferGender.equals("w")){  //여초 선호
                interval += Math.abs((int)(genderService.findOne(code_tmp).getRatio()
                        - genderService.findMin()));
            }else if(preferGender.equals("m")){    //남초 선호
                interval += Math.abs((int)(genderService.findOne(code_tmp).getRatio()
                        - genderService.findMax()));
            }else{  //반반
                interval += Math.abs((int)(genderService.findOne(code_tmp).getRatio()
                        - genderService.getMidValue()));
            }

            //선택 연령대 인구수의 최대값에 가까운
            String age_type = rq.getAge_type();
            interval += ageService.findInterval(code_tmp,age_type);

            intervalList.put(interval, code_tmp);
        }

        topInfoList = sortIntervalList(intervalList);

        return topInfoList;
    }

    //인터벌값 오름차순 정렬(매칭률 top5): 상위 20개 선출 -> 상위 5개 출력
    public List<DongInfo> sortIntervalList(Map<Integer, Long> intervalList){

        List<DongInfo> topInfoList = new ArrayList<>();

        List<Integer> keys = new ArrayList<>(intervalList.keySet());
        Collections.sort(keys);

        int cnt=20;
        if(intervalList.size() < 20){   //분석 결과가 20개 미만인 경우
            cnt = intervalList.size();
        }

        // 결과 출력
        for (int i=0; i<cnt; i++)
        {
            int itv = keys.get(i);
            Long h_code = intervalList.get(keys.get(i));
            String gu = gudongService.findOne(h_code).getGu();
            String h_dong = gudongService.findOne(h_code).getH_dong();

            //System.out.println("interval: "+itv+", h_dong: "+h_dong);

            DongInfo dongInfo = new DongInfo(gu,h_dong,h_code);
            topInfoList.add(dongInfo);
        }

        return topInfoList;
    }

    //상세분석 관련 서비스
    public AnalysisInfo analysisDetail(Long code){

        //평균 전월세가
        int deposit_avg_charter = homeService.findDepositAvgByCharter(code);
        int deposit_avg_monthly = homeService.findDepositAvgByMonthly(code);
        int monthly_avg_monthly = homeService.findMonthlyAvgByMonthly(code);

        //거주 유형
        HomeType homeType = homeTypeService.findOne(code);
        double dandok = homeType.getDandok();
        double apart = homeType.getApart();
        double dasede = homeType.getDasede();
        double officetel = homeType.getOfficetel();

        //성비
        Gender gender = genderService.findOne(code);
        int sum = gender.getMale()+gender.getFemale();
        double man_ratio = Math.round(((double) gender.getMale()/(double) sum*100)*100)/100.0;
        double woman_ratio = Math.round(((double) gender.getFemale()/(double) sum*100)*100)/100.0;
        System.out.println(man_ratio+", "+woman_ratio);

        double child = ageService.findOne(code,"child");
        double s2030 = ageService.findOne(code,"s2030");;
        double s4050 = ageService.findOne(code,"s4050");;
        double elder = ageService.findOne(code,"elder");;

        //매칭률
        double matching_ratio = 0.0;
        //거주자 만족도
        double satisfy_ratio = satisfyService.findOne(code).getValue();

        AnalysisInfo result = new AnalysisInfo(
                  deposit_avg_charter,deposit_avg_monthly,monthly_avg_monthly
                , dandok, apart, dasede, officetel
                , man_ratio,woman_ratio
                , child, s2030, s4050, elder
                , matching_ratio
                , satisfy_ratio);

        return result;
    }

    //거리 오름차순 top4 계산
    public List<DongInfo> sortTopDis(String listByDistance){
        Map<Double,Long> listByDistanceResult = new HashMap<>();
        String[] setArray1 = listByDistance.split("/"); //거리1,코드1
        String[] setArray2;
        for(int i=0; i<setArray1.length; i++){
            setArray2 = setArray1[i].split(",");
            listByDistanceResult.put(Double.parseDouble(setArray2[0]),Long.parseLong(setArray2[1]));
        }

        List<Double> keys = new ArrayList<>(listByDistanceResult.keySet());
        Collections.sort(keys);

        List<DongInfo> topDisInfoList = new ArrayList<>();
        for(int i=0; i<4; i++){
            double dis = Math.ceil((keys.get(i))*100)/100;   //둘쨋자리 까지 반올림
            Long code = listByDistanceResult.get(keys.get(i));
            String gu = gudongService.findOne(code).getGu();
            String dong = gudongService.findOne(code).getH_dong();

            System.out.println("distance: "+dis+", h_dong: "+dong);

            DongInfo dongInfo = new DongInfo(gu,dong,code,dis);
            topDisInfoList.add(dongInfo);
        }

        return  topDisInfoList;
    }

    //조회수 오름차순 top4 계산
    @Transactional
    public void sortTopClick(Long userId, Long h_code){
        List<String> topClickInfoList = new ArrayList<>();

        //파싱된 조회수 맵(top1코드:top1조회수,top2코드:top2조회수, ...)
        Map<Long, Integer> linkedParseClickMap = memberService.parsingClickList(userId);

        //System.out.println("!!!파싱된 맵 결과: "+linkedParseClickMap);
        //조회수 맵에 현재 클릭한 행정동 조회수 처리 여부
        boolean isInsert = false;

        //1. 현재 분석화면에 해당하는 행정동 조회수 카운팅
        for(Long key: linkedParseClickMap.keySet()) {
            //1-1. 현재 조회 맵에 해당 행정동 코드(키 값)가 존재하는 경우 -> 조회수 +1
            if((key.toString()).equals(h_code.toString())){
                linkedParseClickMap.put(key,linkedParseClickMap.get(key)+1);
                isInsert = true;
                break;
            }
        }
        //1-2. 현재 조회수 맵에 해당 행정동 존재하지 않는 경우 -> new코드 : new조회수 추가
        if(!isInsert){
            linkedParseClickMap.put(h_code,1);
        }

        //삽입 후 확인
        for(Long key: linkedParseClickMap.keySet()) {
            System.out.println(key+", "+linkedParseClickMap.get(key));
        }

        //2. 조회수 맵 내림차순 정렬하기(무조건 결과 맵의 길이: 4)
        // Map.Entry 리스트 작성
        List<Map.Entry<Long, Integer>> list_entries = new ArrayList<Map.Entry<Long, Integer>>(linkedParseClickMap.entrySet());

        // 비교함수 Comparator를 사용하여 오름차순으로 정렬
        Collections.sort(list_entries, new Comparator<Map.Entry<Long, Integer>>() {
            // compare로 값을 비교
            public int compare(Map.Entry<Long, Integer> obj1, Map.Entry<Long, Integer> obj2) {
                // 오름 차순 정렬
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });

        System.out.println("내림 차순 정렬 완료");
        linkedParseClickMap.clear();

        //top4로 자르기
        for(int i=0; i<4; i++) {
            if (list_entries.size() <= i) {
                linkedParseClickMap.put(0L, 0);
            } else {
                linkedParseClickMap.put(list_entries.get(i).getKey(), list_entries.get(i).getValue());
            }
        }

        //member 테이블 내 top4 데이터 수정
        for(Long key: linkedParseClickMap.keySet()) {
            System.out.println(key+", "+linkedParseClickMap.get(key));
            topClickInfoList.add(key.toString()+","+linkedParseClickMap.get(key).toString());
        }

        //3. 갱신된 조회 수 데이터 업데이트
        memberService.updateTop4(userId,topClickInfoList);
    }

    public List<DongInfo> analysisTopClick(Long userId){
        //파싱된 조회수 맵(top1코드:top1조회수,top2코드:top2조회수, ...)
        Map<Long, Integer> linkedParseClickMap = memberService.parsingClickList(userId);

        List<DongInfo> resultByClikck = new ArrayList<DongInfo>();
        for(Long code:linkedParseClickMap.keySet()){
            if(code == 0){
                resultByClikck.add(new DongInfo("0","0",0L));
            }else{
                Gudong gudong = gudongService.findOne(code);
                resultByClikck.add(new DongInfo(gudong.getGu(),gudong.getH_dong(),gudong.getH_code()));
            }
        }

        return resultByClikck;
    }
    //조회 수 top4 기반 관련 행정동 추천
}
