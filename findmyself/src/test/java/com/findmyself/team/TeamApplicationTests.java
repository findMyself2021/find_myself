package com.findmyself.team;

import com.findmyself.team.data.domain.Member;
import com.findmyself.team.data.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
class TeamApplicationTests {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void testMember(){
        Member member = new Member();
        member.setDate("2021-05-07");
        Long savedId = memberRepository.save(member);

        Member findMember = memberRepository.findOne(savedId);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());

    }

}
