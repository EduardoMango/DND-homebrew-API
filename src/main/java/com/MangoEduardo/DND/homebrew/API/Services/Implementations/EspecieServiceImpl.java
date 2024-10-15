package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Repositories.EspecieRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEspecieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EspecieServiceImpl implements IEspecieService {

    private final EspecieRepository especieRepository;

    public EspecieServiceImpl(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    @Override
    public Page<EspecieEntity> findAll(Pageable pageable) {
        return especieRepository.findAll(pageable);
    }

    @Override
    public Optional<EspecieEntity> findById(Long id) {
        return especieRepository.findById(id);
    }

    @Override
    public EspecieEntity save(EspecieEntity especieEntity) {
        return especieRepository.save(especieEntity);
    }

    @Override
    public boolean isExist(Long id) {
        return especieRepository.existsById(id);
    }

    @Override
    public EspecieEntity update(Long id, EspecieEntity especieEntity) {

        especieEntity.setIdEspecie(id);
    return especieRepository
        .findById(id)
        .map(
            especieExistente -> {
              // Actualiza solo los campos que no son nulos
              if (especieEntity.getNombreEspecie() != null) {
                especieExistente.setNombreEspecie(especieEntity.getNombreEspecie());
              }
              if (especieEntity.getDescripcionEspecie() != null) {
                especieExistente.setDescripcionEspecie(especieEntity.getDescripcionEspecie());
              }
              if (especieEntity.getRasgos() != null) {
                    especieExistente.setRasgos(especieEntity.getRasgos());
              }
              return especieRepository.save(especieExistente);
            })
        .orElseThrow(() -> new EspecieNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
            especieRepository.deleteById(id);
    }
}
