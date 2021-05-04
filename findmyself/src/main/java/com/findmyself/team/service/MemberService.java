package com.findmyself.team.service;

import com.findmyself.team.data.domain.Member;
import com.findmyself.team.data.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findById(member.getId());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public List<Member> findById(Long id){
        return memberRepository.findById(id);
    }

    //고유 idx로 찾기
    public Member findOne(Long memberIdx){
        return memberRepository.findOne(memberIdx);
    }

    //사용자 조회 수 top4 조회
    public List<String> findTopClickListById(Long id){

        List<String> clickList = new ArrayList<>();
        Member member = findOne(id);
        clickList.add(member.getTop1());
        clickList.add(member.getTop2());
        clickList.add(member.getTop3());
        clickList.add(member.getTop4());

        return clickList;
    }

    // 조회 수 리스트 파싱
    public Map<Long, Integer> parsingClickList(Long id) {

        //사용자 id기반 DB에 저장된 조회수 리스트
        List<String> clickList = findTopClickListById(id);

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

        return linkedParseClickList;

    }
}
