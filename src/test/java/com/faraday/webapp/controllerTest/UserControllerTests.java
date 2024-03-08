package com.faraday.webapp.controllerTest;

import java.io.IOException;
import java.util.Random;

import javax.validation.constraints.AssertTrue;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.stubbing.Answer;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles({"struct1-junit-test"})
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTests {

  MockMvc mockMvc;
  String randomString= "default";

  @Autowired
	private WebApplicationContext wac;

  @BeforeEach
	public void setup() throws JSONException, IOException
	{
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    Random random = new Random();
    randomString = String.valueOf(random.nextInt(1000000));
	}



  /* TEST CHIAMATA END POINT /user/create CASO CREAZIONE AVVENUTA CON SUCCESSO E CASO EMAIL PRESENTE */
	@Test
  @Order(1)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	void test1() throws Exception {

    /* TEST CHIAMATA END POINT /user/create CASO CREAZIONE AVVENUTA CON SUCCESSO */
    String data = "{\r\n" + 
    "	\"e-mail\": \""+randomString+"@email.it\",\r\n" +
    "	\"password\": \"teste1234\",\r\n" +
    "	\"cod-ruolo\":	\"SIMPLE_USER\",\r\n" + 
    "	\"cod-utente-creazione\":	\"JUNIT TEST\"\r\n" + 
    "}";
    mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(data)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isCreated())
    .andExpect(jsonPath("$.code").value("201 CREATED"))
    .andExpect(jsonPath("$.message").value("user creato con successo"));

    /* TEST CHIAMATA END POINT /user/create CASO EMAIL PRESENTE */
    data = "{\r\n" + 
    "	\"e-mail\": \""+randomString+"@email.it\",\r\n" +
    "	\"password\": \"teste1234\",\r\n" +
    "	\"cod-ruolo\":	\"SIMPLE_USER\",\r\n" + 
    "	\"cod-utente-creazione\":	\"JUNIT TEST\"\r\n" + 
    "}";
    mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(data)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotAcceptable())
    .andExpect(jsonPath("$.code").value("406"))
    .andExpect(jsonPath("$.message").value("User con email (email): "+randomString+"@email.it presente a sistema"));
	
	}

  /* TEST CHIAMATA END POINT /user/create CASO ERRORE VALIDAZIONE SIZE PASSWORD  */
	@Test
  @Order(2)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	void test2() throws Exception {
    String data = "{\r\n" + 
    "	\"e-mail\": \"test4@email.it\",\r\n" +
    "	\"password\": \"teste12398087698769876986987\",\r\n" +
    "	\"cod-ruolo\":	\"SIMPLE_USER\",\r\n" + 
    "	\"cod-utente-creazione\":	\"JUNIT TEST\"\r\n" + 
    "}";
    mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(data)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value("400"))
    .andExpect(jsonPath("$.message").value("La password deve contenere minimo 6 massimo 9 caratteri alfanumerici"));
	}

  /* TEST CHIAMATA END POINT /user/create CASO ERRORE VALIDAZIONE PASSWORD NOT EMPTY */
	@Test
  @Order(3)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	void test3() throws Exception {
    String data = "{\r\n" + 
    "	\"e-mail\": \"test4@email.it\",\r\n" +
    "	\"password\": \"\",\r\n" +
    "	\"cod-ruolo\":	\"SIMPLE_USER\",\r\n" + 
    "	\"cod-utente-creazione\":	\"JUNIT TEST\"\r\n" + 
    "}";
    mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(data)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value("400"))
    .andExpect(jsonPath("$.message").value("La password deve contenere minimo 6 massimo 9 caratteri alfanumerici"));
	}

  /* TEST CHIAMATA END POINT /user/create CASO ERRORE VALIDAZIONE EMAIL NOT NULL */
	@Test
  @Order(4)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	void test4() throws Exception {
    String data = "{\r\n" + 
    "	\"password\": \"12345HJN\",\r\n" +
    "	\"cod-ruolo\":	\"SIMPLE_USER\",\r\n" + 
    "	\"cod-utente-creazione\":	\"JUNIT TEST\"\r\n" + 
    "}";
    mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(data)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isBadRequest())
    .andExpect(jsonPath("$.code").value("400"));
  }

  /* TEST CHIAMATA END POINT /user/create CASO RUOLO NON ESISTENTE */
	@Test
  @Order(5)
  @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
	void test5() throws Exception {
    String data = "{\r\n" + 
    "	\"e-mail\": \""+randomString+"@email.it\",\r\n" +
    "	\"password\": \"12345HJN\",\r\n" +
    "	\"cod-ruolo\":	\"ALBERT-E.\",\r\n" + 
    "	\"cod-utente-creazione\":	\"JUNIT TEST\"\r\n" + 
    "}";
    mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
    .contentType(MediaType.APPLICATION_JSON)
    .content(data)
    .accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isNotFound())
    .andExpect(jsonPath("$.code").value("404"))
    .andExpect(jsonPath("$.message").value("Codice ruolo non presente"));
	}

  	private AuthenticationManager createAuthenticationManager() {
		AuthenticationManager am = mock(AuthenticationManager.class);
		given(am.authenticate(any(Authentication.class)))
			.willAnswer((Answer<Authentication>) (invocation) -> (Authentication) invocation.getArguments()[0]);
		return am;
	}

}
