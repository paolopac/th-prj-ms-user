package com.faraday.webapp.exception;

import lombok.Data;

@Data
public class DuplicateException extends Exception{
  private static final long serialVersionUID = 5L;
  private String messaggio = "Entità presente!";

  public DuplicateException(){
    super();
  }

  public DuplicateException(String messaggio){
    super(messaggio);
    this.messaggio = messaggio;
  }

}
