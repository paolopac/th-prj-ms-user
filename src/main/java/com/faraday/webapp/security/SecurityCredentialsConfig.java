package com.faraday.webapp.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean; 
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.faraday.webapp.security.component.SecurityJWT;
import com.faraday.webapp.security.filter.JwtUsernameAndPasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private SecurityJWT jwtConfig;

  private static final String[] AUTH_WHITELIST = {
    "/auth/**", 
    "/actuator/**",
    "/user/create/**",
    "/v2/api-docs",
    "/configuration/ui",
    "/swagger-resources/**",
    "/configuration/security",
    "/swagger-ui.html",
    "/webjars/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // si istruisce spring security di non di non utilizzare sessioni HTTP 
        // FILTRO DI AUTENTICAZIONE
				.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
				// SI DEFINISCONO LE REGOLE DI AUTENTICAZIONE
        .authorizeRequests()
				.antMatchers(AUTH_WHITELIST).permitAll()
				.anyRequest().authenticated();
	}


  // SI CONFIGURANO LE MODALITA' DI REPERIMENTO DELL'UTENTE CON IL QUALE MECCIARE
  // LE CREDENZIALI PERVENUTE E LE MODALITA' DI ENCODIFICA DELLA PASSWORD
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

  // TIPOLOGIA DI ENCODIFICA E CODIFICA DELLA PASSWORD
  // ESSA E' UGUALE ALLA METODOLOGIA UTILIZZATA NEL METODO DI PERSISTENZA DELLA PASSWORD
  // PRESENTE NEL METODO SET DELL'ENTITY An001User
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

}
