package com.faraday.webapp.exception;

import lombok.Data;

@Data
public class BindingException extends Exception{
  private static final long serialVersionUID = 4L;

  private String message = "Errore nella validazione dei dati";

  public BindingException(){
    super();
  }

  public BindingException(String message){
    super(message);
    this.message = message;
  }
}
