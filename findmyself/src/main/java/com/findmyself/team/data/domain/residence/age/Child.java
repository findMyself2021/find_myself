package com.findmyself.team.data.domain.residence.age;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "age_child")
@Getter
@Setter
public class Child {

    @Id
    @GeneratedValue
    private Long h_code;

    private double value;
    private int no;
    private int min;
    private int max;
    private double avg;
}
