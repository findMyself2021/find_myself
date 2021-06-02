package com.findmyself.team.service;

import com.findmyself.team.AnalysisInfo;
import com.findmyself.team.DongInfo;
import com.findmyself.team.TrafficInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.AllByCharter;
import com.findmyself.team.data.domain.AllByMonthly;
import com.findmyself.team.data.domain.Gudong;
import com.findmyself.team.data.domain.home.HomeType;
import com.findmyself.team.data.domain.residence.Gender;
import com.findmyself.team.data.service.*;
import com.findmyself.team.data.service.convenient.ConvenientService;
import com.findmyself.team.data.service.home.HomeService;
import com.findmyself.team.data.service.home.HomeTypeService;
import com.findmyself.team.data.service.residence.age.AgeService;
import com.findmyself.team.data.service.residence.GenderService;
import com.findmyself.team.data.service.traffic.TrafficInfoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnalysisService {

    //매칭률 구하기 위해 인터벌 저장
    Map<Long, Double> intervals = new HashMap<>();
    int std1, std2, std3, std4;

    //전월세 유형 선택 정보
    String home_type;

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

    @Autowired
    AllByCharterService allByCharterService;

    @Autowired
    AllByMonthlyService allByMonthlyService;

    // 필터링 분석
    public HashSet<Long> analysis(Requirements rq){
        HashSet<Long> codeList = new HashSet<>();

        home_type = rq.getHome_type();

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
            System.out.println("homeList size is zero");
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
                if(cnt>=3){
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
                if(cnt>=3){ //조건 모두 충족하면 추천
                    codeList.add(code);
                }
            }
        }

        System.out.println("<<<<<개별군집 조건 충족한>>>>>");
        System.out.println(codeList);

        //매칭률 높은 4개정도만 골라서 하기..?

        if(codeList.size() == 0){
            System.out.println("결과가 없습니다.!!!!!!!!!!!");
        }
        // 필수적인 10개 추천이 안되는 경우
        // allCluster 데이터 내 같은 군집에 속하는 행정동 추가
        if(codeList.size() < 10){

            HashSet<Long> codeList2 = new HashSet<>();

            System.out.println("<<<<<중간 추천 행정동 리스트>>>>>");
            System.out.println(codeList);

            int clusterNo;

            Iterator<Long> iterator = codeList.iterator();
            while (iterator.hasNext()) {
                Long tmpCode = iterator.next();

                if(rq.getHome_type().equals("charter")){
                    clusterNo = allByCharterService.findOne(tmpCode).getNo();
                    for(AllByCharter belong:allByCharterService.findByNo(clusterNo)){
                        codeList2.add(belong.getH_code());
                    }
                }else{
                    clusterNo = allByMonthlyService.findOne(tmpCode).getNo();
                    for(AllByMonthly belong:allByMonthlyService.findByNo(clusterNo)){
                        codeList2.add(belong.getH_code());
                    }
                }
            }

            Iterator<Long> iterator2 = codeList2.iterator();
            while (iterator2.hasNext()) {
                codeList.add(iterator2.next());
            }
        }

        if(codeList.size() < 10){
            System.out.println("여전히 결과가 10개 미만 입니다.");
        }
        System.out.println("<<<<<최종 추천 행정동 리스트>>>>>");
        System.out.println(codeList);
        return codeList;

    }

    // top 매칭률 분석
    // (위 분석에서)추천된 리스트 이용
    public List<DongInfo> findMatchingTop(Requirements rq, HashSet<Long> codeList){

        List<DongInfo> topInfoList;

        Map<Double, Long> intervalList = new HashMap<>();

        double interval = 0;
        // 추출한 추천 행정동 리스트에서 매칭률 높은곳 찾기
        // 인터벌 값이 작을수록 좋음
        for(Long code_tmp: codeList){
            interval = getInterval(rq,code_tmp);
            intervalList.put(interval, code_tmp);
            intervals.put(code_tmp,interval);
        }

        topInfoList = sortIntervalList(intervalList);
        return topInfoList;
    }

    //인터벌값 구하기 함수
    public double getInterval(Requirements rq,Long code_tmp){

        double interval = 0;

        // 전월세 설정값에 가까운
        if(rq.getHome_type().equals("charter")){    //전세 필터링 경우
            interval += Math.abs(rq.getDeposit() - homeService.findDepositAvgByCharter(code_tmp));
        }else{  //월세 필터링 경우
            interval += Math.abs(rq.getDeposit() - homeService.findDepositAvgByMonthly(code_tmp));
            interval += Math.abs(rq.getMonthly() - homeService.findMonthlyAvgByMonthly(code_tmp));
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
        }else{  //"none"
            interval += Math.abs((int)(genderService.findOne(code_tmp).getRatio()
                    - genderService.getMidValue()));
        }

        //선택 연령대 인구수의 최대값에 가까운
        String age_type = rq.getAge_type();
        interval += ageService.findInterval(code_tmp,age_type);

        return interval;
    }

    public void findMatchingStd(){
        double min=1000000000, max=0;

        //System.out.println("intervals: "+intervals);
        for(double itv: intervals.values()){
            if(itv < min){
                min = itv;
            }
            if(itv > max){
                max = itv;
            }
        }
        int tmp = (int)(max - min)/4;
        std1 = (int)min+tmp;
        std2 = std1+tmp;
        std3 = std2+tmp;
        std4 = std3+tmp;

//        System.out.println("min: "+min);
//        System.out.println("max: "+max);
//        System.out.println("std1: "+std1);
//        System.out.println("std2: "+std2);
//        System.out.println("std3: "+std3);
//        System.out.println("std4: "+std4);
    }

    //매칭정도 구하기 by 인터벌값
    public int findMatchingValue(double interval){

        if(interval <= std1){
            return 5;
        }else if(interval <= std2){
            return 4;
        }else if(interval <= std3){
            return 3;
        }else if(interval <= std4){
            return 2;
        }else {
            return 1;
        }
    }

    //인터벌값 오름차순 정렬(매칭률 top): 상위 20개 선출 -> 상위 5개 출력
    public List<DongInfo> sortIntervalList(Map<Double, Long> intervalList){

        List<DongInfo> topInfoList = new ArrayList<>();

        List<Double> keys = new ArrayList<>(intervalList.keySet());
        Collections.sort(keys);

//        System.out.println("<<interval 정렬 결과>>");
//        System.out.println(intervalList);

        int cnt=20;
        if(intervalList.size() < 20){   //분석 결과가 20개 미만인 경우
            cnt = intervalList.size();
        }

        // 결과 출력
        for (int i=0; i<cnt; i++)
        {
            double itv = keys.get(i);
            Long h_code = intervalList.get(keys.get(i));
            String gu = gudongService.findOne(h_code).getGu();
            String h_dong = gudongService.findOne(h_code).getH_dong();

            DongInfo dongInfo = new DongInfo(gu,h_dong,h_code);
            topInfoList.add(dongInfo);
        }

        return topInfoList;
    }

    //상세분석 관련 서비스
    public AnalysisInfo analysisDetail(Long code, Long userId){

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

        //안전 비율(상위 **퍼센트)
        double safety_ratio = safetyService.findRatio(code);

        //편의시설 현황
        int[] convenientArr = convenientService.findValues(code);
        int joy = convenientArr[0];
        int shop = convenientArr[1];
        int food = convenientArr[2];
        int life = convenientArr[3];
        int sport = convenientArr[4];
        int edu = convenientArr[5];

        //성비
        Gender gender = genderService.findOne(code);
        int sum = gender.getMale()+gender.getFemale();
        double man_ratio = Math.round(((double) gender.getMale()/(double) sum*100)*100)/100.0;
        double woman_ratio = Math.round(((double) gender.getFemale()/(double) sum*100)*100)/100.0;
        //System.out.println(man_ratio+", "+woman_ratio);

        double child = ageService.findOne(code,"child");
        double s2030 = ageService.findOne(code,"s2030");;
        double s4050 = ageService.findOne(code,"s4050");;
        double elder = ageService.findOne(code,"elder");;

        //매칭률
        //매칭률 범위 5개로 분할하기
        findMatchingStd();

        double matching_ratio = 0;

        if(intervals.get(code) == null){    //추천 리스트가 아닌 행정동 클릭한 경우(군집 지도에서 클릭..)
            matching_ratio = findMatchingValue(getInterval(memberService.getSettings(userId),code));
        }else{
            matching_ratio = findMatchingValue(intervals.get(code));
        }

//        System.out.println("코드,매칭률: "+code+", "+intervals.get(code));
//        System.out.println("매칭 정도: "+matching_ratio);

        //거주자 만족도
        double satisfy_ratio = satisfyService.findOne(code).getValue();

        AnalysisInfo result = new AnalysisInfo(
                  deposit_avg_charter,deposit_avg_monthly,monthly_avg_monthly
                , dandok, apart, dasede, officetel
                , safety_ratio
                , joy, shop, food, life, sport, edu
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

        int loop_size = 4;
        if(keys.size() < 4){
            loop_size = keys.size();
        }

        for(int i=0; i<loop_size; i++){
            double dis = Math.ceil((keys.get(i))*100)/100;   //둘쨋자리 까지 반올림
            Long code = listByDistanceResult.get(keys.get(i));
            String gu = gudongService.findOne(code).getGu();
            String dong = gudongService.findOne(code).getH_dong();

            //System.out.println("distance: "+dis+", h_dong: "+dong);

            DongInfo dongInfo = new DongInfo(gu,dong,code,dis);
            topDisInfoList.add(dongInfo);
        }

        return  topDisInfoList;
    }

//    //조회수 오름차순 top4 계산
//    @Transactional
//    public void sortTopClick(Long userId, Long h_code){
//        List<String> topClickInfoList = new ArrayList<>();
//
//        //파싱된 조회수 맵(top1코드:top1조회수,top2코드:top2조회수, ...)
//        Map<Long, Integer> linkedParseClickMap = memberService.parsingClickList(userId);
//
//        //System.out.println("!!!파싱된 맵 결과: "+linkedParseClickMap);
//        //조회수 맵에 현재 클릭한 행정동 조회수 처리 여부
//        boolean isInsert = false;
//
//        //1. 현재 분석화면에 해당하는 행정동 조회수 카운팅
//        for(Long key: linkedParseClickMap.keySet()) {
//            //1-1. 현재 조회 맵에 해당 행정동 코드(키 값)가 존재하는 경우 -> 조회수 +1
////            System.out.println("키값: "+ key.toString());
////            System.out.println("클릭된 코드: "+h_code.toString());
////            System.out.println("------------------------------");
//            if((key.toString()).equals(h_code.toString())){
//                linkedParseClickMap.put(key,linkedParseClickMap.get(key)+1);
//                isInsert = true;
//                //System.out.println("+1해야해!: "+ linkedParseClickMap);
//                break;
//            }
//        }
//        //1-2. 현재 조회수 맵에 해당 행정동 존재하지 않는 경우 -> new코드 : new조회수 추가
//        if(!isInsert){
//            linkedParseClickMap.put(h_code,1);
//        }
//
//        //삽입 후 확인
//        for(Long key: linkedParseClickMap.keySet()) {
//            //System.out.println(key+", "+linkedParseClickMap.get(key));
//        }
//
//        //2. 조회수 맵 내림차순 정렬하기(무조건 결과 맵의 길이: 4)
//        // Map.Entry 리스트 작성
//        List<Map.Entry<Long, Integer>> list_entries = new ArrayList<Map.Entry<Long, Integer>>(linkedParseClickMap.entrySet());
//
//        // 비교함수 Comparator를 사용하여 오름차순으로 정렬
//        Collections.sort(list_entries, new Comparator<Map.Entry<Long, Integer>>() {
//            // compare로 값을 비교
//            public int compare(Map.Entry<Long, Integer> obj1, Map.Entry<Long, Integer> obj2) {
//                // 오름 차순 정렬
//                return obj2.getValue().compareTo(obj1.getValue());
//            }
//        });
//
//        //System.out.println("내림 차순 정렬 완료");
//        linkedParseClickMap.clear();
//
//        //top4로 자르기
//        for(int i=0; i<4; i++) {
//            if (list_entries.size() <= i) {
//                linkedParseClickMap.put(0L, 0);
//            } else {
//                linkedParseClickMap.put(list_entries.get(i).getKey(), list_entries.get(i).getValue());
//            }
//        }
//
//        //member 테이블 내 top4 데이터 수정
//        for(Long key: linkedParseClickMap.keySet()) {
//            //System.out.println(key+", "+linkedParseClickMap.get(key));
//            topClickInfoList.add(key.toString()+","+linkedParseClickMap.get(key).toString());
//        }
//        //System.out.println("갱신된 top리스트: "+topClickInfoList);
//
//        //3. 갱신된 조회 수 데이터 업데이트
//        memberService.updateTop4(userId,topClickInfoList);
//    }
//
//    public List<DongInfo> analysisTopClick(Long userId){
//        //파싱된 조회수 맵(top1코드:top1조회수,top2코드:top2조회수, ...)
//        Map<Long, Integer> linkedParseClickMap = memberService.parsingClickList(userId);
//
//        List<DongInfo> resultByClikck = new ArrayList<DongInfo>();
//        for(Long code:linkedParseClickMap.keySet()){
//            if(code == 0){
//                resultByClikck.add(new DongInfo("0","0",0L));
//            }else{
//                Gudong gudong = gudongService.findOne(code);
//                resultByClikck.add(new DongInfo(gudong.getGu(),gudong.getH_dong(),gudong.getH_code()));
//            }
//        }
//
//        return resultByClikck;
//    }

    public List<DongInfo> analysisSimilar(Long code){
        int no;

        double x1,y1,z1;    //현재 분석화면의 대상 행정동 (클러스터)위치 좌표
        double x2,y2,z2;    //동일 클러스터 내 행정동 좌표 대입...

        Map<Double, Long> resultDis = new HashMap<>(){};    //행정동 코드, 좌표간 거리
        List<DongInfo> resultList = new ArrayList<DongInfo>();  //top4 특징 유사한 행정동 정보

        if(home_type.equals("charter")){    //전세 선택후 분석화면 온 경우..
            no = allByCharterService.findOne(code).getNo();
            x1 = allByCharterService.findOne(code).getComponent1();
            y1 = allByCharterService.findOne(code).getComponent2();
            z1 = allByCharterService.findOne(code).getComponent3();

            List<AllByCharter> CharterList = allByCharterService.findByNo(no);
            for(AllByCharter i:CharterList){
                x2 = i.getComponent1();
                y2 = i.getComponent2();
                z2 = i.getComponent3();
                resultDis.put(find3dDis(x1,y1,z1,x2,y2,z2),i.getH_code());
            }
        }else{
            no = allByMonthlyService.findOne(code).getNo();
            x1 = allByMonthlyService.findOne(code).getComponent1();
            y1 = allByMonthlyService.findOne(code).getComponent2();
            z1 = allByMonthlyService.findOne(code).getComponent3();

            List<AllByMonthly> CharterList = allByMonthlyService.findByNo(no);
            for(AllByMonthly i:CharterList){
                x2 = i.getComponent1();
                y2 = i.getComponent2();
                z2 = i.getComponent3();
                resultDis.put(find3dDis(x1,y1,z1,x2,y2,z2),i.getH_code());
            }
        }

        //거리 오름차순 정렬
        List<Double> keys = new ArrayList<>(resultDis.keySet());
        Collections.sort(keys);

        for(int i=1; i<5; i++){ //자기 자신 제외
            Long resultCode = resultDis.get(keys.get(i));
            //System.out.println(resultCode);
            resultList.add(new DongInfo(
                     gudongService.findOne(resultCode).getGu()
                    ,gudongService.findOne(resultCode).getH_dong()
                    ,resultCode
            ));
        }
        //System.out.println(resultDis);
        //System.out.println(resultList);
        return resultList;
    }

    //3차원 좌표 간의 거리 구하기
    public double find3dDis(double x1, double y1, double z1, double x2, double y2, double z2){
        return  Math.sqrt(
                 Math.pow((x1-x2),2)
                +Math.pow((y1-y2),2)
                +Math.pow((z1-z2),2)
        );
    }
}
