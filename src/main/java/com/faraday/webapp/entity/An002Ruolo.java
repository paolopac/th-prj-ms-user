package com.faraday.webapp.entity;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="AN002_RUOLO")
@Data
public class An002Ruolo {


  @Id
  @Column(name = "AN002_ID")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long an002Id;

  @Column(name = "AN002_DESCRIZIONE")
  private String an002Descrizione;

  @Column(name = "AN002_COD_RUOLO")
  private String an002CodRuolo;

  @Column(name = "AN002_DATA_CREAZIONE")
  private Date an002dataCreazione; 

  @Column(name = "AN002_COD_UTENTE_CREAZIONE")
  private String an002CodUtenteCreazione;

  @Column(name = "AN002_STATO")
  private String an002Stato;
 
  @Column(name = "AN002_DATA_MODIFICA")
  private Date an002DataModifica;

  @Column(name = "AN002_COD_UTENTE_MODIFICA")
  private String an002CodUtenteModifica;
  
  /*
  @OneToOne(mappedBy = "ruolo")
  private An001User user;
  */

}
