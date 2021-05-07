package com.findmyself.team.data.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name= "member")
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx; // 기본키

    // 카카오 로그인으로 넘어온 값
    private Long id;

    // 가입 날짜
    private String date;
    
    //조회수 top4
    private String top1;
    private String top2;
    private String top3;
    private String top4;

    public Member(){
        this.top1 = "0,0";
        this.top2 = "0,0";
        this.top3 = "0,0";
        this.top4 = "0,0";
    }
}
