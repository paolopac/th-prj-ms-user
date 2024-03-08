package com.faraday.webapp.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

 
import java.util.Calendar; 
import com.faraday.webapp.entity.An001User;
import com.faraday.webapp.entity.An002Ruolo;
import com.faraday.webapp.repository.An001Repository;
import com.faraday.webapp.repository.An002Repository;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.jasypt.util.text.BasicTextEncryptor;

@SpringBootTest
@ActiveProfiles({"struct1-junit-test"})
@TestMethodOrder(OrderAnnotation.class)
class An001RepositoryTests {

  @Autowired
  An001Repository an001Repository;

  @Autowired
  An002Repository an002Repository;

  BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

  private void initEncryptor() {
    basicTextEncryptor.setPassword("SecondaryKey2024Password!");
  }

  @Test
  @Order(2)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
  void testSaveAn001User(){

    An001User user = new An001User();

    An002Ruolo an002Ruolo = an002Repository.getRuoloByCodRuoloAndStato("SIMPLE_USER","E");
    user.setAn001IdAn002(an002Ruolo.getAn002Id());
    user.setAn001Email("email@email.it");
    user.setAn001CodUtenteCreazione("Test JUNIT");
    this.initEncryptor();
    user.setAn001Password(basicTextEncryptor.encrypt("defaultPassword"));
    user.setAn001DataCreazione(Calendar.getInstance().getTime());
    user.setAn001Stato("E");
    user = an001Repository.save(user);
    assertTrue(user.getAn001Id() != null);

  }
/* 

	@Test
  @Order(1)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	void testCreate() {

    List<String> roles = new ArrayList<String>();
    this.initEncryptor();

    roles.add("ASSOCIATION");
    Users user = new Users();
    user.setUserId("michael.faraday@mail.it");
    user.setPassword(basicTextEncryptor.encrypt("20210101Pa!"));
    user.setAssociationId(19999999);
    user.setRoles(roles);
    usersRepository.save(user);

	}

  @Test
  @Order(2)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
  void testFindByUserId(){

    List<String> roles = new ArrayList<String>();
    this.initEncryptor();

    Users userFinded = usersRepository.findByUserId("michael.faraday@mail.it");

    assertTrue(userFinded.getAssociationId() == 19999999);
    assertTrue(userFinded.getUserId().equals("michael.faraday@mail.it"));
    roles.stream().filter(role -> role.equals("ASSOCIATION"));
    assertTrue(basicTextEncryptor.decrypt(userFinded.getPassword()).equals("20210101Pa!"));

  }

  @Test
  @Order(3)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
  void testUpdate(){

    this.initEncryptor();
    
    Users userToModify = usersRepository.findByUserId("michael.faraday@mail.it");
    userToModify.setPassword(basicTextEncryptor.encrypt("20200101AP!"));
    usersRepository.save(userToModify);

    Users userModified = usersRepository.findByUserId("michael.faraday@mail.it");
    basicTextEncryptor.decrypt(userModified.getPassword()).equals("20200101AP!");

  }

  @Test
  @Order(4)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
  void testSelAll(){

    List<String> roles = new ArrayList<String>();
    this.initEncryptor();

    roles.add("ASSOCIATION");
    roles.add("ADMIN");
    Users user = new Users();
    user.setUserId("paoloacqua@hotmail.it");
    user.setPassword(basicTextEncryptor.encrypt("20210101Pa!"));
    user.setRoles(roles);
    usersRepository.save(user);

    List<Users> users = usersRepository.findAll();
    users.get(0).getUserId().equals("michael.faraday@mail.it");
    users.get(1).getUserId().equals("paoloacqua@hotmail.it");

  }

  @Test
  @Order(5)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
  void testDeleteByUserId(){

    Users userFinded = usersRepository.findByUserId("paoloacqua@hotmail.it");

    usersRepository.delete(userFinded);

    assertTrue(usersRepository.findByUserId("paoloacqua@hotmail.it") == null);

  }

  @Test
  @Order(6)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
  void testDeleteByAssociationId() {

    usersRepository.deleteByAssociationId(19999999);
    assertTrue(usersRepository.findByUserId("michael.faraday@mail.it") == null);

  }  
*/

}
