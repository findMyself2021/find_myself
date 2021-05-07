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

    @PersistenceContext
    private final EntityManager em;

    //    회원 저장
    public Long save(Member member){
        System.out.println("save 함수: "+member);
        em.persist(member);

        return member.getId();
    }

//    public void update(Member member){
//        Member findMember = em.find(Member.class, member.getIdx());
//        findMember.setId(member.getId());
//        findMember.setDate(member.getDate());
//    }

    //기본키로 찾기
    public Member findOne(Long idx){
        return em.find(Member.class,idx);
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

    //카카오 로그인 id로 찾기
    public List<Member> findById(Long id){
        return em.createQuery("select m from Member m where m.id = :id", Member.class)
                .setParameter("id",id)
                .getResultList();
    }
}
