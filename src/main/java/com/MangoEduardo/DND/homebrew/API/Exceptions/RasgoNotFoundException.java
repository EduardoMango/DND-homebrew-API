package com.MangoEduardo.DND.homebrew.API.Exceptions;

import java.util.NoSuchElementException;

public class RasgoNotFoundException extends NoSuchElementException {

  private Long idRasgo;

  public RasgoNotFoundException(Long idRasgo) {
    super("El rasgo con el id " + idRasgo + " no existe");
    this.idRasgo = idRasgo;
  }

  public Long getIdRasgo() {
    return idRasgo;
  }
}
