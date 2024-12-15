package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.SubEspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.SubEspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.EspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Exceptions.SubEspecieNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Repositories.SubEspecieRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.ISubEspecieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubEspecieServiceImpl implements ISubEspecieService {

    private final SubEspecieRepository subEspecieRepository;
    private final IMapper<SubEspecieEntity, SubEspecieDTO> subEspecieMapper;
    private final IMapper<EspecieEntity, EspecieDTO> especieMapper;

    public SubEspecieServiceImpl(SubEspecieRepository subEspecieRepository, IMapper<SubEspecieEntity, SubEspecieDTO> subEspecieMapper, IMapper<EspecieEntity, EspecieDTO> especieMapper) {
        this.subEspecieRepository = subEspecieRepository;
        this.subEspecieMapper = subEspecieMapper;
        this.especieMapper = especieMapper;
    }

    @Override
    public Page<SubEspecieDTO> findAll(Pageable pageable) {
        Page<SubEspecieEntity> subEspecieEntities = subEspecieRepository.findAll(pageable);


        List<SubEspecieDTO> nonDeletedEntities = subEspecieEntities.getContent().stream()
                .filter(subEspecieEntity -> !Boolean.TRUE.equals(subEspecieEntity.isEstaBorrado()))
                .map(subEspecieMapper::mapTo)
                .toList();
        return new PageImpl<>(nonDeletedEntities, pageable, subEspecieEntities.getTotalElements());}

    @Override
    public Page<SubEspecieDTO> findByEspecie(EspecieDTO especie, Pageable pageable) {
        return subEspecieRepository.findByEspecie(especieMapper.mapFrom(especie), pageable).map(subEspecieMapper::mapTo);
    }

    @Override
    public SubEspecieDTO findById(Long id) {
        Optional<SubEspecieEntity> found = subEspecieRepository.findById(id);

        if(found.isPresent() && !Boolean.TRUE.equals(found.get().isEstaBorrado())) {
            return subEspecieMapper.mapTo(found.get());
        }
            throw new SubEspecieNotFoundException(id);

    }

    @Override
    public SubEspecieDTO save(EspecieDTO especie, SubEspecieDTO subEspecieDTO) {

        SubEspecieEntity toSave = subEspecieMapper.mapFrom(subEspecieDTO);
        toSave.setEspecie(especieMapper.mapFrom(especie));
        SubEspecieEntity saved = subEspecieRepository.save(toSave);

        return subEspecieMapper.mapTo(saved);
    }

    @Override
    public boolean isExist(Long id) {
        return subEspecieRepository.existsById(id);
    }

    @Override
    public SubEspecieDTO update(Long id, SubEspecieDTO subEspecieDTO) {
        subEspecieDTO.setIdSubespecie(id);
        SubEspecieEntity updated = subEspecieRepository
                .findById(id)
                .map(
                        subEspecieExistente -> {
                            // Actualiza solo los campos que no son nulos
                            if (subEspecieDTO.getNombreSubespecie() != null) {
                                subEspecieExistente.setNombreSubespecie(subEspecieDTO.getNombreSubespecie());
                            }
                            if (subEspecieDTO.getDescripcionSubespecie() != null) {
                                subEspecieExistente.setDescripcionSubespecie(subEspecieDTO.getDescripcionSubespecie());
                            }
                            if (subEspecieDTO.getRasgos() != null) {
                                subEspecieExistente.setRasgos(subEspecieDTO.getRasgos());
                            }
                            return subEspecieRepository.save(subEspecieExistente);
                        })
                .orElseThrow(() -> new EspecieNotFoundException(id));

        return subEspecieMapper.mapTo(updated);
    }

    @Override
    public void delete(Long id) {
        Optional<SubEspecieEntity> subEspecieEntity = subEspecieRepository.findById(id);
        if (subEspecieEntity.isPresent()) {
            SubEspecieEntity toBeDeleted = subEspecieEntity.get();
            toBeDeleted.setEstaBorrado(true);
            subEspecieRepository.save(toBeDeleted);
        } else {
            throw new SubEspecieNotFoundException(id);
        }
    }
}
