package com.faraday.webapp.security.service;

import java.net.URI;
import java.net.URISyntaxException;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.faraday.webapp.entity.An001User;
import com.faraday.webapp.entity.An002Ruolo;
import com.faraday.webapp.exception.UserNotFoundException;
import com.faraday.webapp.services.An001Service;
import com.faraday.webapp.services.An002Service;
 
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Autowired
  private An001Service an001Service;

  @Autowired
  private An002Service an002Service;

  // TALE METODO E' RICHIAMATO AUTOMATICAMENTE IN FASE DI AUTENTICAZIONE UTENTE 
  // PERMETTE DI REPERIRE L'UTENTE, DATA EMAIL, DALLA DATABSE LOCALE 
	@Override
	public UserDetails loadUserByUsername(String email) {
		
	  if (email == null) { 
        throw new UsernameNotFoundException("email non valido"); 
	  } 
		
    // REPERMENTO USER
		An001User an001User = an001Service.findByEmailAndStato(email, "E");
    if (an001User == null) { 
        throw new UsernameNotFoundException("Utente non trovato"); 
		}

    An002Ruolo an002Ruolo = an002Service.findByIdAndStato(an001User.getAn001IdAn002(), "E");
    if (an002Ruolo == null) { 
        throw new UsernameNotFoundException("Id ruolo non trovato"); 
		}
		
    // SI ISTANZIA UN OGGETTO DI TIPO UserBuilder CONTENENTE LE CREDENZIALI ED IL ROLE
    // DELL'UTENTE IDENTIFICATO
		UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(an001User.getAn001Email());
		builder.disabled((false));
		builder.password(an001User.getAn001Password());		 
    String[] roles = new String[]{ "ROLE_"+an002Ruolo.getAn002CodRuolo()};
    builder.authorities(roles);
    return builder.build();
		 
  }
  	
}
