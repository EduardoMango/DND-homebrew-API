package com.MangoEduardo.DND.homebrew.API.Exceptions;

import java.util.NoSuchElementException;

public class HechizoNotFoundException extends NoSuchElementException {

    private Long id_hechizo;

    public HechizoNotFoundException(String message, Long id_hechizo) {
        super(message);
        this.id_hechizo = id_hechizo;
    }

    public HechizoNotFoundException(Long id_hechizo) {
        super("El hechizo con el id "+id_hechizo+" no existe");
    this.id_hechizo=id_hechizo;
    }

    public Long getId_hechizo() {
        return id_hechizo;
    }

}
