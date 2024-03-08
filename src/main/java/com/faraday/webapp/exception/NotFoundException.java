package com.faraday.webapp.exception;


public class NotFoundException extends Exception {

  private static final long serialVersionUID = 3L;

  private String messaggio = "Elemento non trovato";

  public NotFoundException(){
    super();
  }

  public NotFoundException(String messaggio){
    super(messaggio);
    this.messaggio =messaggio; 
  }

    /**
     * @return String return the messaggio
     */
    public String getMessaggio() {
        return messaggio;
    }

    /**
     * @param messaggio the messaggio to set
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }

}