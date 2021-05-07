package com.findmyself.team.service;

import com.findmyself.team.AnalysisInfo;
import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Convenient;
import com.findmyself.team.data.domain.Member;
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

    @Autowired
    MemberService memberService;

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

        //return topClickInfoList;
    }
    
    //조회 수 top4 기반 관련 행정동 추천
}
