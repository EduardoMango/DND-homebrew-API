package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Enums.DamageTypes;
import com.MangoEduardo.DND.homebrew.API.Exceptions.HechizoNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Repositories.EscuelaMagiaRepository;
import com.MangoEduardo.DND.homebrew.API.Repositories.HechizoRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IHechizoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HechizoServiceImpl implements IHechizoService {

    private final HechizoRepository hechizoRepository;
    private final EscuelaMagiaRepository escuelaMagiaRepository;
    private final IMapper<HechizoEntity, HechizoDTO> hechizoMapper;

    HechizoServiceImpl(HechizoRepository hechizoRepository, EscuelaMagiaRepository escuelaMagiaRepository, IMapper<HechizoEntity, HechizoDTO> hechizoMapper){
        this.hechizoRepository = hechizoRepository;
        this.escuelaMagiaRepository = escuelaMagiaRepository;
        this.hechizoMapper = hechizoMapper;
    }

    @Override
    public Page<HechizoDTO> findAll(Pageable pageable) {
        Page<HechizoEntity> hechizoEntities = hechizoRepository.findAll(pageable);
        List<HechizoDTO> nonDeletedEntities = hechizoEntities.getContent().stream()
                .filter(hechizo -> !Boolean.TRUE.equals(hechizo.getEstaBorrado()))
                .map(hechizoMapper::mapTo)
                .toList();

    return new PageImpl<>(nonDeletedEntities,pageable,hechizoEntities.getTotalElements());
    }

    @Override
    public HechizoDTO findById(Long id) {
        Optional<HechizoEntity> hechizo = hechizoRepository.findById(id);

        //Boolean.True.Equals(hechizo.get().getEstaBorrado()) es una forma null safe de verificar si es true
        if (hechizo.isPresent() && !Boolean.TRUE.equals(hechizo.get().getEstaBorrado())) {
            return hechizoMapper.mapTo(hechizo.get());
        }
        throw new HechizoNotFoundException(id);
    }

    public Page<HechizoDTO> findByNombreHechizo(String nombre_hechizo, Pageable pageable) {
        return hechizoRepository
                .findByNombreHechizoContainingIgnoreCase(nombre_hechizo, pageable)
                .map(hechizoMapper::mapTo);
    }

    @Override
    public HechizoDTO save(HechizoDTO hechizoDTO) {
        HechizoEntity saved = hechizoRepository
                .save(hechizoMapper.mapFrom(hechizoDTO));

        return hechizoMapper.mapTo(saved);
    }

    @Override
    public boolean isExist(Long id) {
        return hechizoRepository.existsById(id);
    }

    @Override
    public HechizoDTO update(Long id, HechizoDTO hechizoDTO) {

        hechizoDTO.setId_hechizo(id);

        HechizoEntity updated = hechizoRepository.findById(id).map(hechizoExistente -> {
            // Actualiza solo los campos que no son nulos
            if (hechizoDTO.getId_hechizo() != null) {
                hechizoExistente.setId_hechizo(hechizoDTO.getId_hechizo());
            }
            if (hechizoDTO.getNombreHechizo() != null) {
                hechizoExistente.setNombreHechizo(hechizoDTO.getNombreHechizo());
            }
            if (hechizoDTO.getNivelHechizo() != null) {
                hechizoExistente.setNivelHechizo(hechizoDTO.getNivelHechizo());
            }
            if (hechizoDTO.getDescripcion_hechizo() != null) {
                hechizoExistente.setDescripcion_hechizo(hechizoDTO.getDescripcion_hechizo());
            }
            if (hechizoDTO.getNiveles_mayores() != null) {
                hechizoExistente.setNiveles_mayores(hechizoDTO.getNiveles_mayores());
            }
            if (hechizoDTO.getTiempo_casteo() != null) {
                hechizoExistente.setTiempo_casteo(hechizoDTO.getTiempo_casteo());
            }
            if (hechizoDTO.getDuracion_Hechizo() != null) {
                hechizoExistente.setDuracion_Hechizo(hechizoDTO.getDuracion_Hechizo());
            }
            if (hechizoDTO.getRango_texto() != null) {
                hechizoExistente.setRango_texto(hechizoDTO.getRango_texto());
            }
            if (hechizoDTO.getRango() != null) {
                hechizoExistente.setRango(hechizoDTO.getRango());
            }
            if (hechizoDTO.getArea() != null) {
                hechizoExistente.setArea(hechizoDTO.getArea());
            }
            if (hechizoDTO.getTipo_objetivo() != null) {
                hechizoExistente.setTipo_objetivo(hechizoDTO.getTipo_objetivo());
            }
            if (hechizoDTO.getMaterial_requerido() != null) {
                hechizoExistente.setMaterial_requerido(hechizoDTO.getMaterial_requerido());
            }
            if (hechizoDTO.getMaterial_costo() != null) {
                hechizoExistente.setMaterial_costo(hechizoDTO.getMaterial_costo());
            }
            if (hechizoDTO.getHabilidad_tirada_salvacion() != null) {
                hechizoExistente.setHabilidad_tirada_salvacion(hechizoDTO.getHabilidad_tirada_salvacion());
            }
            if (hechizoDTO.getDanio() != null) {
                hechizoExistente.setDanio(hechizoDTO.getDanio());
            }
            if (hechizoDTO.getDamageTypes() != null) {
                hechizoExistente.setDamageTypes(hechizoDTO.getDamageTypes());
            }

            //Sets all boolean values
            hechizoExistente.setEsRitual(hechizoDTO.isEsRitual());
            hechizoExistente.setEsAtaque(hechizoDTO.isEsAtaque());
            hechizoExistente.setVerbal(hechizoDTO.isVerbal());
            hechizoExistente.setSomantico(hechizoDTO.isSomantico());
            hechizoExistente.setMaterial(hechizoDTO.isMaterial());
            hechizoExistente.setConcentracion(hechizoDTO.isConcentracion());
            hechizoExistente.setTiradaSalvacion(hechizoDTO.isTiradaSalvacion());

            // Sets the EscuelaMagia
            if (hechizoDTO.getEscuelaMagia() != null) {
                Optional <EscuelaMagiaEntity> escuela = escuelaMagiaRepository.findById(hechizoDTO.getEscuelaMagia().getId_escuela());
                escuela.ifPresent(hechizoExistente::setEscuelaMagia);

            }
            return hechizoRepository.save(hechizoExistente);
        }).orElseThrow(() -> new HechizoNotFoundException(id));

    return hechizoMapper.mapTo(updated);
    }

    @Override
    public void delete(Long id) {
        Optional<HechizoEntity> hechizo = hechizoRepository.findById(id);
        if (hechizo.isPresent()){
            HechizoEntity entity = hechizo.get();
            entity.setEstaBorrado(true);
            hechizoRepository.save(entity);
        }
        throw new HechizoNotFoundException(id);
    }

    @Override
    public Page<HechizoDTO> findHechizosByEscuelaId(Long idEscuela, Pageable pageable) {
        return hechizoRepository
                .findHechizosByEscuelaId(idEscuela, pageable)
                .map(hechizoMapper::mapTo);
    }

    @Override
    public Page<HechizoDTO> findByNivelHechizo(Integer nivelHechizo, Pageable pageable) {
        return hechizoRepository
                .findByNivelHechizo(nivelHechizo, pageable)
                .map(hechizoMapper::mapTo);
    }

    @Override
    public Page<HechizoDTO> findByDamageTypes(DamageTypes damageTypes, Pageable pageable) {
        return hechizoRepository
                .findByDamageTypesContaining(damageTypes, pageable)
                .map(hechizoMapper::mapTo);
    }
}
