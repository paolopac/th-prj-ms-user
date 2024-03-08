package com.faraday.webapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name="AN001_USER")
@Data
public class An001User {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="AN001_ID")
  private Long an001Id;
  
  @Column(name="AN001_EMAIL")
  private String an001Email;
 
  @Column(name="AN001_PASSWORD")
  private String an001Password;
 
  @Column(name = "AN001_ID_AN002")
  private Long an001IdAn002;

  @Column(name="AN001_STATO")
  private String an001Stato;

  @Column(name="AN001_DATA_CREAZIONE")
  private Date an001DataCreazione;
 
  @Column(name="AN001_COD_UTENTE_CREAZIONE")
  private String an001CodUtenteCreazione;

  @Column(name="AN001_DATA_MODIFICA")
  private Date an001DataModifica;

  @Column(name="AN001_COD_UTENTE_MODIFICA")
  private String an001CodUtenteModifica;

}
