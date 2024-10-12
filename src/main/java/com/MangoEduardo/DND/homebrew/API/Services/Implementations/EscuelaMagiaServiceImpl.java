package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Repositories.EscuelaMagiaRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEscuelaMagiaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class EscuelaMagiaServiceImpl implements IEscuelaMagiaService {

    private final EscuelaMagiaRepository escuelaMagiaRepository;

    public EscuelaMagiaServiceImpl(EscuelaMagiaRepository escuelaMagiaRepository) {
        this.escuelaMagiaRepository = escuelaMagiaRepository;
    }

    @Override
    public Page<EscuelaMagiaEntity> findAll(Pageable pageable) {
        return escuelaMagiaRepository.findAll(pageable);
    }

    @Override
    public Optional<EscuelaMagiaEntity> findById(Long id) {
        return escuelaMagiaRepository.findById(id);
    }

    @Override
    public EscuelaMagiaEntity save(EscuelaMagiaEntity escuelaMagiaEntity) {
        return escuelaMagiaRepository.save(escuelaMagiaEntity);
    }

    @Override
    public boolean isExist(Long id) {
        return escuelaMagiaRepository.existsById(id);
    }

    @Override
    public EscuelaMagiaEntity update(Long id, EscuelaMagiaEntity escuelaMagiaEntity) {
        escuelaMagiaEntity.setId_escuela(id);

        return escuelaMagiaRepository.findById(id).map(escuelaMagiaExistente -> {
            // Actualiza solo los campos que no son nulos
            if (escuelaMagiaEntity.getNombreEscuela() != null) {
                escuelaMagiaExistente.setNombreEscuela(escuelaMagiaEntity.getNombreEscuela());
            }
            if (escuelaMagiaEntity.getDescripcion_escuela() != null) {
                escuelaMagiaExistente.setDescripcion_escuela(escuelaMagiaEntity.getDescripcion_escuela());
            }
            return escuelaMagiaRepository.save(escuelaMagiaExistente);
        }).orElseThrow(() -> new EntityNotFoundException("La escuela ingresada no existe"));
    }

    @Override
    public void delete(Long id) {
        escuelaMagiaRepository.deleteById(id);
    }

    @Override
    public Page<EscuelaMagiaEntity> findByNombreEscuela(String nombreEscuela, Pageable pageable) {
        return escuelaMagiaRepository.findByNombreEscuelaContainingIgnoreCase(nombreEscuela, pageable);
    }
}
