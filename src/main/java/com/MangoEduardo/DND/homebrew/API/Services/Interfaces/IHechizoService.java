package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Enums.DamageTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IHechizoService {

    //Devuelve un page con todos los hechizos
    Page<HechizoDTO> findAll(Pageable pageable);

    //Busca un hechizo por ID y devuelve un optional
    HechizoDTO findById(Long id);

    //Busca un hechizo por nombre y devuelve un page
    Page<HechizoDTO> findByNombreHechizo(String nombre_hechizo, Pageable pageable);

    //Guarda un hechizo recibido en la base de datos y lo devuelve
    HechizoDTO save(HechizoDTO hechizoDTO);

    //Verifica por ID si un hechizo existe en la bd
    boolean isExist(Long id);

    //Actualiza parcialmente los datos de un hechizo
    HechizoDTO update(Long id, HechizoDTO hechizoDTO);

    //Elimina un hechizo por ID
    void delete(Long id);

    Page<HechizoDTO> findHechizosByEscuelaId(Long idEscuela, Pageable pageable);

    Page<HechizoDTO> findByNivelHechizo(Integer nivelHechizo, Pageable pageable);

    Page<HechizoDTO> findByDamageTypes(DamageTypes damageTypes, Pageable pageable);
}
