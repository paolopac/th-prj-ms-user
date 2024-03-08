package com.faraday.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.faraday.webapp.entity.An002Ruolo;

@Repository
public interface An002Repository extends JpaRepository<An002Ruolo, Long>{

  @Query(value = "SELECT * FROM AN002_RUOLO"
  + " WHERE AN002_COD_RUOLO= ?1"
  + " AND AN002_STATO= ?2", nativeQuery = true)
  An002Ruolo getRuoloByCodRuoloAndStato(String codRuolo,  String stato);

  An002Ruolo findByAn002IdAndAn002Stato(Long an002Id,  String stato);
  
}
