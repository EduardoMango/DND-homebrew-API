package com.MangoEduardo.DND.homebrew.API.Exceptions;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {

  private Long userID;

  public UserNotFoundException(Long userID) {
    super("El usuario con el id " + userID + " no existe");
    this.userID = userID;
  }
}
