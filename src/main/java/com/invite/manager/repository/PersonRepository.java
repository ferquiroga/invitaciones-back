package com.invite.manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invite.manager.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}