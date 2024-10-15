package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEspecieService {

    Page<EspecieEntity> findAll(Pageable pageable);
    Optional<EspecieEntity> findById(Long id);
    EspecieEntity save(EspecieEntity especieEntity);
    boolean isExist(Long id);
    EspecieEntity update(Long id, EspecieEntity especieEntity);
    void delete(Long id);


}
