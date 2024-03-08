package com.faraday.webapp.services;

import com.faraday.webapp.dto.request.CreateUserDTOReq;
import com.faraday.webapp.entity.An001User;
import com.faraday.webapp.entity.An002Ruolo;
import com.faraday.webapp.exception.DuplicateException;
import com.faraday.webapp.exception.NotFoundException;
import com.faraday.webapp.repository.An001Repository;
import com.faraday.webapp.repository.An002Repository;

import lombok.extern.java.Log;

import java.util.Calendar;

import org.jasypt.util.text.BasicTextEncryptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Log
@Service
public class An001ServiceImpl implements An001Service {

  @Autowired
  private An001Repository an001Repository;

  @Autowired
  private An002Repository an002Repository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private ResourceBundleMessageSource messager;

  @Override
  public An001User create(CreateUserDTOReq user) throws NotFoundException, DuplicateException {
    
    // QUALITY CHECK VERIFICA ESISTENZA USER
    An001User userFinded = an001Repository.getUserByEmailAndStato(user.getAn001Email(),"E");
    if (null != userFinded) {
      String errMsg = String.format(messager.getMessage("duplicate.User.byEmail", null, LocaleContextHolder.getLocale()), user.getAn001Email());
      log.warning(errMsg);
      throw new DuplicateException(errMsg);
    }

    // CONVERSIONE UserDTOReq > An001User
    An001User an001User = modelMapper.map(user, An001User.class);
    An002Ruolo an002Ruolo = an002Repository.getRuoloByCodRuoloAndStato(user.getCodRuolo(), "E");
    if(an002Ruolo == null){
      String errMsg = messager.getMessage("NotFoundException.An002Ruolo.An002CodRuolo", null, LocaleContextHolder.getLocale());
      log.warning(errMsg);
      throw new NotFoundException(errMsg);
    }   
    an001User.setAn001Password(new BCryptPasswordEncoder().encode(user.getAn001Password()));
    an001User.setAn001IdAn002(an002Ruolo.getAn002Id());
    an001User.setAn001DataCreazione(Calendar.getInstance().getTime());
    an001User.setAn001Stato("E");
    // INSERIMENTO NUOVO UTENTE
    an001User = an001Repository.save(an001User);
    return an001User;
  }

  @Override
  public An001User findByEmailAndStato(String email, String stato) {
    return an001Repository.getUserByEmailAndStato(email, stato);
  }

  /* 
  @Override
  @Transactional
  @Caching(evict = { // PA: IN CASO DI ATTIVAZIONE CACHE SI VERIFICHI @Transactional(readOnly = true)
    @CacheEvict(value="selByUserIdCache", allEntries=true),
    @CacheEvict(value="selByIdCache", allEntries=true),
    @CacheEvict(value="selByAssociationIdCache", allEntries=true), 
    @CacheEvict(value="selAllCache", allEntries=true)
  })
  public Users insUsers(Users user){

    return usersRepository.save(user);

  }

  @Override
  @Cacheable(value="selByUserIdCache", key="{#userId}", sync=true)
  public Users selByUserId(String userId){

    return usersRepository.findByUserId(userId);

  }
  
  @Override
  @Cacheable(value="selByIdCache", key="{#id}", sync=true)
  public Optional<Users> selById(String id){

    return usersRepository.findById(id);
    
  }

  @Override
  @Transactional
  @Caching(evict = {
    @CacheEvict(value="selByUserIdCache", allEntries=true),
    @CacheEvict(value="selByIdCache", allEntries=true),
    @CacheEvict(value="selByAssociationIdCache", allEntries=true), 
    @CacheEvict(value="selAllCache", allEntries=true)
  })
  public Users updateUsers(Users user) {

    return usersRepository.save(user);

  }

  @Override
  @Transactional
  @Caching(evict = {
    @CacheEvict(value="selByUserIdCache", key="#userId"),
    @CacheEvict(value="selByIdCache", allEntries=true),
    @CacheEvict(value="selByAssociationIdCache", allEntries=true), 
    @CacheEvict(value="selAllCache", allEntries=true)
  })
  public Users deleteByUserId(String userId) {

    return usersRepository.deleteByUserId(userId);

  }

  @Override
  @Cacheable(value="selByAssociationIdCache", key="{#associationId}", sync=true)
  public Users selByAssociationId(int associationId){

    return usersRepository.findByAssociationId(associationId);

  }

  @Override
  @Transactional
  @Caching(evict = {
    @CacheEvict(value="selByUserIdCache", allEntries=true),
    @CacheEvict(value="selByIdCache", allEntries=true),
    @CacheEvict(value="selByAssociationIdCache", key = "#associationId"), 
    @CacheEvict(value="selAllCache", allEntries=true)
  })
  public Users deleteByAssociationId(int associationId) {

    return usersRepository.deleteByAssociationId(associationId);

  }

  @Override
  @Cacheable(value="selAllCache", sync=true)
  public List<Users> selAll() {

    return usersRepository.findAll();

  }*/


}
