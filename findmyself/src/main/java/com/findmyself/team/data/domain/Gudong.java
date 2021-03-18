package com.findmyself.team.data.domain;

import com.findmyself.team.data.service.GudongService;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name= "gu_dong_code")
@Getter @Setter
public class Gudong {

    @Id
    @GeneratedValue
    private Long h_code;

    private String gu;
    private String h_dong;

    public Gudong() {

    }

}
