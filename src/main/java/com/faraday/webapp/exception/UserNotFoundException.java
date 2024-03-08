package com.faraday.webapp.exception;

import lombok.Data;

@Data
public class UserNotFoundException extends Exception {
  private static final long serialVersionUID = 4L;

  private String message = "Email non presente";

  public UserNotFoundException(String message){
    super(message);
    this.message = message;
  }
}
