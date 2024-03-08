package com.faraday.webapp.services;

import com.faraday.webapp.dto.request.CreateUserDTOReq;
import com.faraday.webapp.entity.An001User;
import com.faraday.webapp.exception.DuplicateException;
import com.faraday.webapp.exception.NotFoundException;

public interface An001Service {

  An001User findByEmailAndStato(String email, String stato);

  An001User create(CreateUserDTOReq user) throws NotFoundException, DuplicateException;
  
  /* 

  Users insUsers(Users user);

  Optional<Users> selById(String id);

  Users selByUserId(String userId);

  Users selByAssociationId(int i);

  Users updateUsers(Users User);

  Users deleteByUserId(String userId);

  Users deleteByAssociationId(int associationId);

  List<Users> selAll();
  */
  
}
