package com.findmyself.team.data.repository;

import com.findmyself.team.data.domain.Convenient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public interface ConvenientRepository extends CrudRepository<Convenient, Long> {

}
