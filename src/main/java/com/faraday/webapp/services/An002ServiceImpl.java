package com.faraday.webapp.services;

import com.faraday.webapp.dto.request.CreateUserDTOReq;
import com.faraday.webapp.entity.An001User;
import com.faraday.webapp.entity.An002Ruolo;
import com.faraday.webapp.exception.DuplicateException;
import com.faraday.webapp.exception.NotFoundException;
import com.faraday.webapp.repository.An001Repository;
import com.faraday.webapp.repository.An002Repository;

import lombok.extern.java.Log;

 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 

@Log
@Service
public class An002ServiceImpl implements An002Service {

  @Autowired
  private An002Repository an002Repository;

  @Override
  public An002Ruolo findByCodRuoloAndStato(String codRuolo, String stato) {
    return an002Repository.getRuoloByCodRuoloAndStato(codRuolo, stato);
  }

  @Override
  public An002Ruolo findByIdAndStato(Long id, String stato) {
    return an002Repository.findByAn002IdAndAn002Stato(id, stato);
  }

}
