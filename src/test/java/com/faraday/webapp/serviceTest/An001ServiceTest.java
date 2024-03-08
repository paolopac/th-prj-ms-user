package com.faraday.webapp.serviceTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.faraday.webapp.dto.request.CreateUserDTOReq;
import com.faraday.webapp.entity.An001User;
import com.faraday.webapp.exception.DuplicateException;
import com.faraday.webapp.exception.NotFoundException;
import com.faraday.webapp.services.An001Service;

@SpringBootTest
@ActiveProfiles({"struct1-junit-test"})
@TestMethodOrder(OrderAnnotation.class)
public class An001ServiceTest {

  @Autowired
  private An001Service an001Service;


  /* NOTE: SI TESTA LA NORMALE CREAZIONE */
  @Test
  @Order(1)
  void test1() throws NotFoundException, DuplicateException{
    CreateUserDTOReq userDTOReq = new CreateUserDTOReq();
    userDTOReq.setAn001Email("test@email.it");
    userDTOReq.setAn001Password("test2024");
    userDTOReq.setCodRuolo("SIMPLE_USER");
    userDTOReq.setAn001CodUtenteCreazione("JUNIT-TEST");
    An001User an001User = an001Service.create(userDTOReq);
    assertTrue(an001User.getAn001Id() != null);
  }

  /* NOTE: SI TESTA L'ECCEZZIONE EMAIL GIA' PRESENTE */
  @Test
  @Order(2)
  void test2() throws NotFoundException{
    CreateUserDTOReq userDTOReq = new CreateUserDTOReq();
    userDTOReq.setAn001Email("test@email.it");
    userDTOReq.setAn001Password("test2024");
    userDTOReq.setCodRuolo("SIMPLE_USER");
    userDTOReq.setAn001CodUtenteCreazione("JUNIT-TEST");
    An001User an001User;
    try {
      an001User = an001Service.create(userDTOReq);
    } catch (DuplicateException e) {
      assertTrue(e.getMessage().toString().equals("User con email (email): test@email.it presente a sistema"));
    }
  }

  /* NOTE: SI TESTA L'ECCEZZIONE RUOLO NON PRESENTE */
  @Test
  @Order(3)
  void test3() throws DuplicateException {
    CreateUserDTOReq userDTOReq = new CreateUserDTOReq();
    userDTOReq.setAn001Email("test_albert@email.it");
    userDTOReq.setAn001Password("test2024");
    userDTOReq.setCodRuolo("RUOLO NON ESISTENTE");
    userDTOReq.setAn001CodUtenteCreazione("JUNIT-TEST");
    An001User an001User;
    try {
      an001User = an001Service.create(userDTOReq);
    } catch (NotFoundException e) {
      assertTrue(e.getMessage().toString().equals("Codice ruolo non presente"));
    }
  }
  
}
