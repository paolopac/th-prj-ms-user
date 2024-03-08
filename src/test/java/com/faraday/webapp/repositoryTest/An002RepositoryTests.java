package com.faraday.webapp.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.faraday.webapp.Application;
import com.faraday.webapp.entity.An002Ruolo; 
import com.faraday.webapp.repository.An002Repository;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;  

@SpringBootTest
@ActiveProfiles({"struct1-junit-test"})
@TestMethodOrder(OrderAnnotation.class)
@ContextConfiguration(classes = { Application.class })

class An002RepositoryTests {

  @Autowired
  An002Repository an002Repository;

  @Test

  @Order(1)
  void testGetRuoloByCodRuoloAndStato(){
    An002Ruolo an002Ruolo = an002Repository.getRuoloByCodRuoloAndStato("SIMPLE_USER","E");
    assertTrue(an002Ruolo != null);
  }

}
