package com.faraday.webapp.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CreateUserDTOReq {
  
  @JsonProperty("e-mail")
  @NotEmpty(message ="{CreateUserDTOReq.validation.notEmpty.an001Email}")
  @NotNull(message ="{CreateUserDTOReq.validation.notNull.an001Email}")
  private String an001Email;
 
  @JsonProperty("password")
  @NotNull(message ="{CreateUserDTOReq.validation.notNull.an001Password}")
  @Size(min=6, max=9, message="{CreateUserDTOReq.validation.size.an001Password}")
  private String an001Password;
 
  @JsonProperty("cod-ruolo")
  @NotNull(message ="{CreateUserDTOReq.validation.notNull.codRuolo}")
  @NotEmpty(message ="{CreateUserDTOReq.validation.notEmpty.codRuolo}")
  private String codRuolo;

  @JsonProperty("cod-utente-creazione")
  @NotNull(message ="{CreateUserDTOReq.validation.notNull.codUtenteCreazione}")
  @NotEmpty(message ="{CreateUserDTOReq.validation.notEmpty.codUtenteCreazione}")
  private String an001CodUtenteCreazione;



}
