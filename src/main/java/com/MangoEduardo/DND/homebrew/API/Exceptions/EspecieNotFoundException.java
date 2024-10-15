package com.MangoEduardo.DND.homebrew.API.Exceptions;

import java.util.NoSuchElementException;

public class EspecieNotFoundException extends NoSuchElementException {

    private Long idEspecie;

    public EspecieNotFoundException(String message, Long idEspecie) {
        super(message);
        this.idEspecie = idEspecie;
    }

    public EspecieNotFoundException(Long idEspecie) {
        super("La especie con el id " + idEspecie + " no existe");
        this.idEspecie = idEspecie;
    }

    public Long getIdEspecie() {
        return idEspecie;
    }
}
