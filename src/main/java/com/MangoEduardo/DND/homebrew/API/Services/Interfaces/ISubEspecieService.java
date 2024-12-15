package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.SubEspecieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISubEspecieService {

    Page<SubEspecieDTO> findAll(Pageable pageable);
    Page<SubEspecieDTO> findByEspecie(EspecieDTO especie, Pageable pageable);
    SubEspecieDTO findById(Long id);

    SubEspecieDTO save(EspecieDTO especie,SubEspecieDTO subEspecieDTO);
    boolean isExist(Long id);
    SubEspecieDTO update(Long id, SubEspecieDTO subEspecieDTO);
    void delete(Long id);

}
