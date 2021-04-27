package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //    회원 저장
    public void save(Member member){
        em.persist(member);
    }

    //    회원 찾기(단건 조회)
    public Member findOne(Long idx){
        return em.find(Member.class,idx);
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
