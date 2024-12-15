package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Repositories.EspecieRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IEspecieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieServiceImpl implements IEspecieService {

    private final EspecieRepository especieRepository;
    private final IMapper<EspecieEntity, EspecieDTO> especieMapper;

    public EspecieServiceImpl(EspecieRepository especieRepository, IMapper<EspecieEntity, EspecieDTO> especieMapper) {
        this.especieRepository = especieRepository;
        this.especieMapper = especieMapper;
    }

    @Override
    public Page<EspecieDTO> findAll(Pageable pageable) {
        Page<EspecieEntity> especieEntities = especieRepository.findAll(pageable);

        List<EspecieDTO> nonDeletedEntities = especieEntities.getContent().stream()
                .filter(especie -> !Boolean.TRUE.equals(especie.getEstaBorrado()))
                .map(especieMapper::mapTo)
                .toList();

        return new PageImpl<>(nonDeletedEntities, pageable, especieEntities.getTotalElements());
    }

    @Override
    public EspecieDTO findById(Long id) {

        Optional<EspecieEntity> found = especieRepository.findById(id);

        if (found.isPresent() && !Boolean.TRUE.equals(found.get().getEstaBorrado())) {
            return especieMapper.mapTo(found.get());
        }
        throw new EspecieNotFoundException(id);
    }

    @Override
    public EspecieDTO save(EspecieDTO especieDTO) {
        EspecieEntity saved = especieRepository
                .save(especieMapper.mapFrom(especieDTO));
        return especieMapper.mapTo(saved);
    }

    @Override
    public boolean isExist(Long id) {
        return especieRepository.existsById(id);
    }

    @Override
    public EspecieDTO update(Long id, EspecieDTO especieDTO) {

        especieDTO.setIdEspecie(id);
    EspecieEntity updated = especieRepository
        .findById(id)
        .map(
            especieExistente -> {
              // Actualiza solo los campos que no son nulos
              if (especieDTO.getNombreEspecie() != null) {
                especieExistente.setNombreEspecie(especieDTO.getNombreEspecie());
              }
              if (especieDTO.getDescripcionEspecie() != null) {
                especieExistente.setDescripcionEspecie(especieDTO.getDescripcionEspecie());
              }
              if (especieDTO.getRasgos() != null) {
                    especieExistente.setRasgos(especieDTO.getRasgos());
              }
              return especieRepository.save(especieExistente);
            })
        .orElseThrow(() -> new EspecieNotFoundException(id));

    return especieMapper.mapTo(updated);
    }

    @Override
    public void delete(Long id) {
        if (!especieRepository.existsById(id)) {
            throw new EspecieNotFoundException(id);
        }
        EspecieEntity toBeDeleted = especieRepository.findById(id).get();
        toBeDeleted.setEstaBorrado(true);
        especieRepository.save(toBeDeleted);
    }
}
