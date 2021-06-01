package com.findmyself.team.service;

import com.findmyself.team.ConvenientTmp;
import com.findmyself.team.Requirements;
import com.findmyself.team.data.domain.Member;
import com.findmyself.team.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@PersistenceContext
public class MemberService {
    private final MemberRepository memberRepository;
    private EntityManager em;

    public Member findOneById(Long id){
        return memberRepository.findOneById(id);
    }
    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        System.out.println("중복 통과");
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findMembers(member.getId());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //이전 설정값 가져오기
    public Requirements getSettings(Long id){
        Member member = memberRepository.findOneById(id);
        return  new Requirements(
                 member.getHome_type()
                ,member.getDeposit()
                ,member.getMonthly()
                ,member.getAddress()
                ,member.getTraffic()
                ,new ConvenientTmp(
                        Math.round((member.getJoy()
                                + member.getLife()
                                + member.getShop()
                                + member.getSport()
                                + member.getFood()
                                + member.getEdu())/6)
                        ,member.getJoy()
                        ,member.getLife()
                        ,member.getShop()
                        ,member.getSport()
                        ,member.getFood()
                        ,member.getEdu()
                )
                ,member.getSafety()
                ,member.getSex_ratio()
                ,member.getAge_type()
        );
    }

    //설정값 저장하기
    @Transactional
    public void setSettings(Long id, Requirements rq){
        //카카오 id로 멤버 찾기
        Member member = memberRepository.findOneById(id);

        member.setHome_type(rq.getHome_type());
        member.setDeposit(rq.getDeposit());
        member.setMonthly(rq.getMonthly());
        member.setAddress(rq.getAddress());
        member.setTraffic(rq.getTraffic());
        member.setSafety(rq.getSafety());
        member.setSex_ratio(rq.getSex_ratio());
        member.setAge_type(rq.getAge_type());

        member.setJoy(rq.getJoy());
        member.setLife(rq.getLife());
        member.setShop(rq.getShop());
        member.setSport(rq.getSport());
        member.setFood(rq.getFood());
        member.setEdu(rq.getEdu());
    }



//    //사용자 조회 수 top4 조회
//    public List<String> findTopClickListById(Long id){
//
//        List<String> clickList = new ArrayList<>();
//        Member member = findOneById(id);
//        clickList.add(member.getTop1());
//        clickList.add(member.getTop2());
//        clickList.add(member.getTop3());
//        clickList.add(member.getTop4());
//
//        return clickList;
//    }
//
//    // 조회 수 리스트 파싱
//    public Map<Long, Integer> parsingClickList(Long id) {
//
//        //사용자 id기반 DB에 저장된 조회수 리스트
//        List<String> clickList = findTopClickListById(id);
//        //System.out.println("클릭리스트 조회결과: "+clickList);
//
//        //파싱된 조회수 맵(top1코드:top1조회수,top2코드:top2조회수, ...)
//        Map<Long, Integer> linkedParseClickMap = new LinkedHashMap<>();
//
//        for(String i:clickList){
//            //System.out.println("클릭리스트 원소 추적: "+i);
//            int deli = i.indexOf(",");
//
//            if(deli == -1){ //,로 구분된 문자열 존재 X -> 조회한 행정동 X
//                System.out.println(",로 구분된 문자열 존재 X");
//            }else{
//                linkedParseClickMap.put(
//                         Long.parseLong(i.substring(0,deli))   //행정동 코드
//                        ,Integer.parseInt(i.substring(deli+1))   //조회수
//                );
//            }
//        }
//        //System.out.println("클릭리스트 파싱 결과맵: "+linkedParseClickMap);
//
//        return linkedParseClickMap;
//
//    }
//
//    //조회 수 top4 리스트 데이터 갱신
//    @Transactional
//    public void updateTop4(Long id, List<String> topClickInfoList){
//
//        //카카오 id로 멤버 찾기
//        Member member = memberRepository.findOneById(id);
//
//        int size = topClickInfoList.size();
//
//        if(size>0){
//            member.setTop1(topClickInfoList.get(0));
//            //System.out.println(member.getTop1());
//        }
//        if(size>1){
//            member.setTop2(topClickInfoList.get(1));
//            //System.out.println(member.getTop2());
//        }
//        if(size>2){
//            member.setTop3(topClickInfoList.get(2));
//        }
//        if(size>3){
//            member.setTop4(topClickInfoList.get(3));
//        }
//    }
}
