package com.MangoEduardo.DND.homebrew.API.Exceptions;

import java.util.NoSuchElementException;

public class EscuelaMagiaNotFoundException extends NoSuchElementException {

    private Long id_escuela;

    public EscuelaMagiaNotFoundException(String message, Long id_escuela) {
        super(message);
        this.id_escuela = id_escuela;
    }

    public EscuelaMagiaNotFoundException(Long id_escuela) {
        super("La escuela con el id " + id_escuela + " no existe");
        this.id_escuela = id_escuela;
    }

    public Long getId_escuela() {
        return id_escuela;
    }
}
