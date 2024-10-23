package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.SubEspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Repositories.SubEspecieRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.ISubEspecieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubEspecieServiceImpl implements ISubEspecieService {

    private final SubEspecieRepository subEspecieRepository;

    public SubEspecieServiceImpl(SubEspecieRepository subEspecieRepository) {
        this.subEspecieRepository = subEspecieRepository;
    }

    @Override
    public Page<SubEspecieEntity> findAll(Pageable pageable) {
        return subEspecieRepository.findAll(pageable);
    }

    @Override
    public Page<SubEspecieEntity> findByEspecie(EspecieEntity especie, Pageable pageable) {
        return subEspecieRepository.findByEspecie(especie, pageable);
    }

    @Override
    public Optional<SubEspecieEntity> findById(Long id) {
        return subEspecieRepository.findById(id);
    }

    @Override
    public SubEspecieEntity save(SubEspecieEntity subEspecieEntity) {
        return subEspecieRepository.save(subEspecieEntity);
    }

    @Override
    public boolean isExist(Long id) {
        return subEspecieRepository.existsById(id);
    }

    @Override
    public SubEspecieEntity update(Long id, SubEspecieEntity subEspecieEntity) {
        subEspecieEntity.setIdSubespecie(id);
        return subEspecieRepository
                .findById(id)
                .map(
                        subEspecieExistente -> {
                            // Actualiza solo los campos que no son nulos
                            if (subEspecieEntity.getNombreSubespecie() != null) {
                                subEspecieExistente.setNombreSubespecie(subEspecieEntity.getNombreSubespecie());
                            }
                            if (subEspecieEntity.getDescripcionSubespecie() != null) {
                                subEspecieExistente.setDescripcionSubespecie(subEspecieEntity.getDescripcionSubespecie());
                            }
                            if (subEspecieEntity.getRasgos() != null) {
                                subEspecieExistente.setRasgos(subEspecieEntity.getRasgos());
                            }
                            return subEspecieRepository.save(subEspecieExistente);
                        })
                .orElseThrow(() -> new EspecieNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        subEspecieRepository.deleteById(id);
    }
}
