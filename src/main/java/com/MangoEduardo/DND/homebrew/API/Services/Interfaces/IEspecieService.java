package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.EspecieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IEspecieService {

    Page<EspecieDTO> findAll(Pageable pageable);
    EspecieDTO findById(Long id);
    EspecieDTO save(EspecieDTO especieDTO);
    boolean isExist(Long id);
    EspecieDTO update(Long id, EspecieDTO especieDTO);
    void delete(Long id);


}
