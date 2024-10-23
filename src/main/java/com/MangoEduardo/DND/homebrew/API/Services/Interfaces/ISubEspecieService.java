package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.SubEspecieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ISubEspecieService {

    Page<SubEspecieEntity> findAll(Pageable pageable);
    Page<SubEspecieEntity> findByEspecie(EspecieEntity especie, Pageable pageable);
    Optional<SubEspecieEntity> findById(Long id);

    SubEspecieEntity save(SubEspecieEntity subEspecieEntity);
    boolean isExist(Long id);
    SubEspecieEntity update(Long id, SubEspecieEntity subEspecieEntity);
    void delete(Long id);

}
