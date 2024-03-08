package com.faraday.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.faraday.webapp.entity.An001User;

@Repository
public interface An001Repository extends JpaRepository<An001User, Long>{

  @Query(value = "SELECT * FROM AN001_USER"
  + " WHERE AN001_EMAIL= ?1"
  + " AND AN001_STATO= ?2", nativeQuery = true)
  An001User getUserByEmailAndStato(String eMail,  String stato);

}
