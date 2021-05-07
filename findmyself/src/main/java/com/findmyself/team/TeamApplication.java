package com.findmyself.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class TeamApplication {
    public static void main(String[] args) {

        SpringApplication.run(TeamApplication.class, args);
    }
}
