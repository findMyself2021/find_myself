package com.findmyself.team.service;

import com.findmyself.team.DongInfo;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.service.GudongService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AnalysisServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    AnalysisService analysisService;
    @Autowired
    GudongService gudongService;

    @Test
    public void 필터링된_리스트_반환(){

        Requirements rq = new Requirements().defaultRequirements();
        List<Long> result = analysisService.analysis(rq);
        for(long code:result){
            System.out.println("코드:"+code+", 행정동:"+gudongService.findNameByCode(code));
        }
    }

    @Test
    public void 조회맵_작동_확인(){
        Long h_code = 3333L;

        List<DongInfo> topClickInfoList = new ArrayList<>();

        //파싱된 조회수 맵(top1코드:top1조회수,top2코드:top2조회수, ...)
        Map<Long, Integer> linkedParseClickMap = parsingClickList();

        //조회수 맵에 현재 클릭한 행정동 조회수 처리 여부
        boolean isInsert = false;

        //1. 현재 분석화면에 해당하는 행정동 조회수 카운팅
        for(Long key: linkedParseClickMap.keySet()) {
            //1-1. 현재 조회 맵에 해당 행정동 코드(키 값)가 존재하는 경우 -> 조회수 +
            System.out.println("keySet의 key: "+key);
            System.out.println("h_code 값: "+ h_code);
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
        int cut_cnt;
        if(list_entries.size() > 4){
            cut_cnt = 4;
        }else{
            cut_cnt = list_entries.size();
        }
        for(int i=0; i<cut_cnt; i++){
            linkedParseClickMap.put(list_entries.get(i).getKey(), list_entries.get(i).getValue());
        }

        //내림차순 top4 정렬 후 확인
        for(Long key: linkedParseClickMap.keySet()) {
            System.out.println(key+", "+linkedParseClickMap.get(key));

        }

        //3. 조회수 오름차순 DongInfo 객체 생성 후, 결과 리스트 반환


        //return  topClickInfoList;
    }

    // 조회 수 리스트 파싱
    public Map<Long, Integer> parsingClickList() {

        //사용자 id기반 DB에 저장된 조회수 리스트
        List<String> clickList = new ArrayList<>();
        clickList.add("1111,3");
        clickList.add("2222,1");
        clickList.add("3333,5");
        clickList.add("4444,2");
        clickList.add("5555,7");

        //파싱된 조회수 맵(top1코드:top1조회수,top2코드:top2조회수, ...)
        Map<Long, Integer> linkedParseClickList = new LinkedHashMap<>();

        for(String i:clickList){
            int deli = i.indexOf(",");
            if(deli == -1){ //,로 구분된 문자열 존재 X -> 조회한 행정동 X
                System.out.println(",로 구분된 문자열 존재 X");

                linkedParseClickList.put(0L,0);
            }else{
                linkedParseClickList.put(
                        Long.parseLong(i.substring(0,deli))   //행정동 코드
                        ,Integer.parseInt(i.substring(deli+1))   //조회수
                );
            }
        }

        //System.out.println(linkedParseClickList);

        return linkedParseClickList;
    }
}