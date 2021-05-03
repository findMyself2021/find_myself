package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name= "member")
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long idx;

    // 가입 날짜
    private String date;

    // 카카오 로그인으로 넘어온 값
    private Long id;

    //조회수 top4
    private String top1;
    private String top2;
    private String top3;
    private String top4;

}
