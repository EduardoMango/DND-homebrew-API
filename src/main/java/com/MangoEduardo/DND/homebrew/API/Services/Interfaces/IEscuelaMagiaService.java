package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface IEscuelaMagiaService {

    Page<EscuelaMagiaEntity> findAll(Pageable pageable);

    Optional<EscuelaMagiaEntity> findById(Long id);

    EscuelaMagiaEntity save(EscuelaMagiaEntity escuelaMagiaEntity);

    boolean isExist(Long id);

    EscuelaMagiaEntity update(Long id, EscuelaMagiaEntity escuelaMagiaEntity);
    void delete(Long id);

    Page<EscuelaMagiaEntity> findByNombreEscuela(String nombreEscuela, Pageable pageable);
}
