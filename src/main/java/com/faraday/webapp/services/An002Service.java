package com.faraday.webapp.services;

import com.faraday.webapp.entity.An002Ruolo;

public interface An002Service {

  An002Ruolo findByCodRuoloAndStato(String codRuolo, String stato);

  An002Ruolo findByIdAndStato(Long id, String stato);

}
