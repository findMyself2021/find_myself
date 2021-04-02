package com.findmyself.team.data.domain.residence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gender")
@Getter @Setter
public class ResidenceGender {

    @Id
    @GeneratedValue
    private Long h_code;

    private int male;
    private int female;
    private double sex_ratio;

    public ResidenceGender(){

    }
}