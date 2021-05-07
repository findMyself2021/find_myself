package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //    회원 저장
    public void save(Member member){
        System.out.println("save 함수: "+member);
        em.persist(member);
    }

    //카카오 로그인 id로 찾기
    public Member findOne(Long id){
        return em.find(Member.class,id);
    }

    //중복 회원 검증
    public List<Member> findMembers(Long id){
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id",id)
                .getResultList();
    }

    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
