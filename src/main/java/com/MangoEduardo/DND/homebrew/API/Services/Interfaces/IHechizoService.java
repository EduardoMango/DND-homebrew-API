package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Models.DamageTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHechizoService {

    //Devuelve un page con todos los hechizos
    Page<HechizoEntity> findAll(Pageable pageable);

    //Busca un hechizo por ID y devuelve un optional
    Optional<HechizoEntity> findById(Long id);

    //Busca un hechizo por nombre y devuelve un page
    Page<HechizoEntity> findByNombreHechizo(String nombre_hechizo, Pageable pageable);

    //Guarda un hechizo recibido en la base de datos y lo devuelve
    HechizoEntity save(HechizoEntity hechizoEntity);

    //Verifica por ID si un hechizo existe en la bd
    boolean isExist(Long id);

    //Actualiza parcialmente los datos de un hechizo
    HechizoEntity update(Long id, HechizoEntity hechizoEntity);

    //Elimina un hechizo por ID
    void delete(Long id);

    Page<HechizoEntity> findHechizosByEscuelaId(Long idEscuela, Pageable pageable);

    Page<HechizoEntity> findByNivelHechizo(Integer nivelHechizo, Pageable pageable);

    Page<HechizoEntity> findByDamageTypes(DamageTypes damageTypes, Pageable pageable);
}
