package com.faraday.webapp.security.filter;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.faraday.webapp.dto.response.ErrorDTORes;
import com.faraday.webapp.security.component.SecurityJWT;
import com.faraday.webapp.security.dto.request.AutheticationDTOReq;
import com.faraday.webapp.security.dto.response.AutheticationDTORes;
import com.fasterxml.jackson.databind.ObjectMapper; 

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
	private static final Logger logger = LoggerFactory.getLogger(JwtUsernameAndPasswordAuthenticationFilter.class);
	
	private AuthenticationManager authManager;

	private final SecurityJWT securityJWT;
	
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authManager, SecurityJWT securityJWT) {
		this.authManager = authManager;
		this.securityJWT = securityJWT;

		// By default, UsernamePasswordAuthenticationFilter listens to "/login" path.
		// In our case, we use "/auth". So, we need to override the defaults.
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(securityJWT.getUrl(), "POST"));
	}

  // TALE METODO E' AVVIATO AUTOMATICAMENTE IN FASE DI VERIFICA AUTENTICAZIONE 
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
	  try {

			// 1. SI ESTRAGGONO LE CREDENZIALI DALLA REQUEST
			AutheticationDTOReq userCredentials = new ObjectMapper().readValue(request.getInputStream(), AutheticationDTOReq.class);
			
			logger.info("UserEmail: " + userCredentials.getUsername() + "  Password: **********");

			// 2. SI CREA L'OGGETTO AUTH CONTENENTE LE CREDENZIALI DI ACCESSO, CHE SARÀ USATO DAL AUTH MANAGER
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userCredentials.getUsername(),
      userCredentials.getPassword(), Collections.emptyList());
			
			// 3. SI UTILIZZA IL METODO loaduserbyusername(), PRESENTE ALL'INTERNO DI customUserDetailSservice 
			// PER APPLICARE L'AUTENTICAZIONE
      return authManager.authenticate(authToken);
		} 
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// TALE METODO E' ESEGUITO AUTOMATICAMENTE IN CASO DI AUTENTICAZIONE ESEGUITA CON SUCCESSO
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
		Long now = System.currentTimeMillis();
    // SI CREA IL TOKEN
		String token = Jwts.builder().setSubject(auth.getName())
				.claim("authorities", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + securityJWT.getExpiration() * 1000))																							 
				.signWith(SignatureAlgorithm.HS512, securityJWT.getSecret().getBytes()).compact();

		String fullToken = "Token: " + securityJWT.getPrefix() + " " + token;

		// SI INSERISCE IL TOKEN ALL'INTERNO DELL'HEADER
		response.addHeader(securityJWT.getHeader(), fullToken);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    // SI INSERISCE IL TOKEN ALL'INTERNO DEL BODY
    AutheticationDTORes autheticationDTORes = new AutheticationDTORes();
    autheticationDTORes.setToken(token);
    new ObjectMapper().writeValue(response.getOutputStream(), autheticationDTORes);
	}

  // TALE METODO E' ESEGUITO, AUTOMATICAMENTE, IN CASO DI AUTHENTICATION FAIL
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      ErrorDTORes customErrorResponse = new ErrorDTORes();
      customErrorResponse.setDate(null);
      customErrorResponse.setCode(HttpServletResponse.SC_UNAUTHORIZED);
      customErrorResponse.setMessage(failed.getMessage());
      new ObjectMapper().writeValue(response.getOutputStream(), customErrorResponse);
  }

}
