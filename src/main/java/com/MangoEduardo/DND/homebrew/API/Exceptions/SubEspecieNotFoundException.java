package com.MangoEduardo.DND.homebrew.API.Exceptions;

import java.util.NoSuchElementException;

public class SubEspecieNotFoundException extends NoSuchElementException {

  private Long id_subespecie;

  public SubEspecieNotFoundException(Long id_subespecie) {
    super("La subespecie con el id " + id_subespecie + " no existe");
    this.id_subespecie = id_subespecie;
  }

  public Long getId_subespecie() {
    return id_subespecie;
  }
}
