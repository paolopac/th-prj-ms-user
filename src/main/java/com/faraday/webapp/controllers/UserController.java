package com.faraday.webapp.controllers;

import javax.validation.Valid;

import com.faraday.webapp.dto.request.CreateUserDTOReq; 
import com.faraday.webapp.exception.BindingException;
import com.faraday.webapp.exception.DuplicateException;
import com.faraday.webapp.exception.NotFoundException;

import com.faraday.webapp.services.An001Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import lombok.extern.java.Log;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/user")
@Log
public class UserController {

  @Autowired
  private ResourceBundleMessageSource messager;

  @Autowired
  private An001Service an001Service;


  @ApiOperation(
    value = "Creazione utente", 
    notes = "Inserisce un nuovo utente", 
    produces = "application/json")
  @ApiResponses(value = { 
    @ApiResponse(code = 201, message = "User inserito correttamente"),
    @ApiResponse(code = 400, message = "Dati inseriti non validi"),
    @ApiResponse(code = 406, message = "Email presente") 
  })
  @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
  public ResponseEntity<?> createNewUser(
    @ApiParam(
      name = "user", 
      type = "CreateUserDTOReq", 
      value = "Presenta dati riguardante l'utente", 
      example = "", 
      required = true
    ) @Valid @RequestBody CreateUserDTOReq user, BindingResult bindingResult) throws BindingException, DuplicateException, NotFoundException {
        
    String errMsg = String.format(messager.getMessage("user.pre.create", null, LocaleContextHolder.getLocale()), user.getAn001Email());

    log.info(errMsg);

    // QUALITY CHECK OBBLIGATORIETA'
    if (bindingResult.hasErrors()) {
      errMsg = messager.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
      log.warning(errMsg);
      throw new BindingException(errMsg);
    }
    // INSERIMENTO USER
    try {
      an001Service.create(user);
    } catch (NotFoundException notFoundException) {
      throw new NotFoundException(notFoundException.getMessage());
    } catch (DuplicateException duplicateException) {
      throw new DuplicateException(duplicateException.getMessage());
    }

    HttpHeaders header = new HttpHeaders();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode responseNode = objectMapper.createObjectNode();

    header.setContentType(MediaType.APPLICATION_JSON);
    responseNode.put("code", HttpStatus.CREATED.toString());
    responseNode.put("message", String.format(messager.getMessage("user.create.ok", null, LocaleContextHolder.getLocale())));
    return new ResponseEntity<>(responseNode, header, HttpStatus.CREATED);
    
  }
  
}
