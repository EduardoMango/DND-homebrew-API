package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.EscuelaMagiaDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EscuelaMagiaNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Repositories.EscuelaMagiaRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEscuelaMagiaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EscuelaMagiaServiceImpl implements IEscuelaMagiaService {

    private final EscuelaMagiaRepository escuelaMagiaRepository;
    private final IMapper<EscuelaMagiaEntity, EscuelaMagiaDTO> escuelaMagiaMapper;

    public EscuelaMagiaServiceImpl(EscuelaMagiaRepository escuelaMagiaRepository, IMapper<EscuelaMagiaEntity, EscuelaMagiaDTO> escuelaMagiaMapper) {
        this.escuelaMagiaRepository = escuelaMagiaRepository;
        this.escuelaMagiaMapper = escuelaMagiaMapper;
    }

    @Override
    public Page<EscuelaMagiaDTO> findAll(Pageable pageable) {
        Page<EscuelaMagiaEntity> escuelas = escuelaMagiaRepository
                .findAll(pageable);

        List<EscuelaMagiaDTO> nonDeletedEntities = escuelas.getContent().stream()
                .filter(escuela -> !Boolean.TRUE.equals(escuela.getEstaBorrado()))
                .map(escuelaMagiaMapper::mapTo)
                .toList();

        return new PageImpl<>(nonDeletedEntities, pageable, escuelas.getTotalElements());
    }

    @Override
    public EscuelaMagiaDTO findById(Long id) {
        Optional<EscuelaMagiaEntity> found = escuelaMagiaRepository.findById(id);

        if (found.isPresent() && !Boolean.TRUE.equals(found.get().getEstaBorrado())) {
            return escuelaMagiaMapper.mapTo(found.get());
        }
        throw new EscuelaMagiaNotFoundException(id);

    }

    @Override
    public EscuelaMagiaDTO save(EscuelaMagiaDTO escuelaMagiaDTO) {
        EscuelaMagiaEntity saved = escuelaMagiaRepository.save(escuelaMagiaMapper.mapFrom(escuelaMagiaDTO));
        return escuelaMagiaMapper.mapTo(saved);
    }

    @Override
    public boolean isExist(Long id) {
        return escuelaMagiaRepository.existsById(id);
    }

    @Override
    public EscuelaMagiaDTO update(Long id, EscuelaMagiaDTO escuelaMagiaDTO) {
        escuelaMagiaDTO.setId_escuela(id);

        EscuelaMagiaEntity updated = escuelaMagiaRepository.findById(id).map(escuelaMagiaExistente -> {
            // Actualiza solo los campos que no son nulos
            if (escuelaMagiaDTO.getNombreEscuela() != null) {
                escuelaMagiaExistente.setNombreEscuela(escuelaMagiaDTO.getNombreEscuela());
            }
            if (escuelaMagiaDTO.getDescripcion_escuela() != null) {
                escuelaMagiaExistente.setDescripcion_escuela(escuelaMagiaDTO.getDescripcion_escuela());
            }
            return escuelaMagiaRepository.save(escuelaMagiaExistente);
        }).orElseThrow(() -> new EntityNotFoundException("La escuela ingresada no existe"));

    return escuelaMagiaMapper.mapTo(updated);
    }

    @Override
    public void delete(Long id) {

        Optional <EscuelaMagiaEntity> toBeDeleted = escuelaMagiaRepository.findById(id);
        if (toBeDeleted.isPresent()) {
            EscuelaMagiaEntity entity = toBeDeleted.get();
            entity.setEstaBorrado(true);
            escuelaMagiaRepository.save(entity);
        }
            throw new EscuelaMagiaNotFoundException(id);
    }

    @Override
    public Page<EscuelaMagiaDTO> findByNombreEscuela(String nombreEscuela, Pageable pageable) {
        return escuelaMagiaRepository.findByNombreEscuelaContainingIgnoreCase(nombreEscuela, pageable).map(escuelaMagiaMapper::mapTo);
    }
}
