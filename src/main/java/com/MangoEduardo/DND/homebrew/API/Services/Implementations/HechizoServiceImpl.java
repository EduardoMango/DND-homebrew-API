package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Models.DamageTypes;
import com.MangoEduardo.DND.homebrew.API.Repositories.HechizoRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IHechizoService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HechizoServiceImpl implements IHechizoService {

    private final HechizoRepository hechizoRepository;

    HechizoServiceImpl(HechizoRepository hechizoRepository){
        this.hechizoRepository = hechizoRepository;
    }

    @Override
    public Page<HechizoEntity> findAll(Pageable pageable) {
        return hechizoRepository.findAll(pageable);
    }

    @Override
    public Optional<HechizoEntity> findById(Long id) {
        return hechizoRepository.findById(id);
    }

    public Page<HechizoEntity> findByNombreHechizo(String nombre_hechizo, Pageable pageable) {
        return hechizoRepository.findByNombreHechizoContainingIgnoreCase(nombre_hechizo, pageable);
    }

    @Override
    public HechizoEntity save(HechizoEntity hechizoEntity) {
        return hechizoRepository.save(hechizoEntity);
    }

    @Override
    public boolean isExist(Long id) {
        return hechizoRepository.existsById(id);
    }

    @Override
    public HechizoEntity update(Long id, HechizoEntity hechizoEntity) {

        hechizoEntity.setId_hechizo(id);

        return hechizoRepository.findById(id).map(hechizoExistente -> {
            // Actualiza solo los campos que no son nulos
            if (hechizoEntity.getId_hechizo() != null) {
                hechizoExistente.setId_hechizo(hechizoEntity.getId_hechizo());
            }
            if (hechizoEntity.getNombreHechizo() != null) {
                hechizoExistente.setNombreHechizo(hechizoEntity.getNombreHechizo());
            }
            if (hechizoEntity.getNivelHechizo() != null) {
                hechizoExistente.setNivelHechizo(hechizoEntity.getNivelHechizo());
            }
            if (hechizoEntity.getDescripcion_hechizo() != null) {
                hechizoExistente.setDescripcion_hechizo(hechizoEntity.getDescripcion_hechizo());
            }
            if (hechizoEntity.getNiveles_mayores() != null) {
                hechizoExistente.setNiveles_mayores(hechizoEntity.getNiveles_mayores());
            }
            if (hechizoEntity.getTiempo_casteo() != null) {
                hechizoExistente.setTiempo_casteo(hechizoEntity.getTiempo_casteo());
            }
            if (hechizoEntity.getDuracion_Hechizo() != null) {
                hechizoExistente.setDuracion_Hechizo(hechizoEntity.getDuracion_Hechizo());
            }
            if (hechizoEntity.getRango_texto() != null) {
                hechizoExistente.setRango_texto(hechizoEntity.getRango_texto());
            }
            if (hechizoEntity.getRango() != null) {
                hechizoExistente.setRango(hechizoEntity.getRango());
            }
            if (hechizoEntity.getArea() != null) {
                hechizoExistente.setArea(hechizoEntity.getArea());
            }
            if (hechizoEntity.getTipo_objetivo() != null) {
                hechizoExistente.setTipo_objetivo(hechizoEntity.getTipo_objetivo());
            }
            if (hechizoEntity.isVerbal()) {
                hechizoExistente.setVerbal(hechizoEntity.isVerbal());
            }
            if (hechizoEntity.isSomantico()) {
                hechizoExistente.setSomantico(hechizoEntity.isSomantico());
            }
            if (hechizoEntity.isMaterial()) {
                hechizoExistente.setMaterial(hechizoEntity.isMaterial());
            }
            if (hechizoEntity.getMaterial_requerido() != null) {
                hechizoExistente.setMaterial_requerido(hechizoEntity.getMaterial_requerido());
            }
            if (hechizoEntity.getMaterial_costo() != null) {
                hechizoExistente.setMaterial_costo(hechizoEntity.getMaterial_costo());
            }
            if (hechizoEntity.isConcentracion()) {
                hechizoExistente.setConcentracion(hechizoEntity.isConcentracion());
            }
            if (hechizoEntity.isTirada_salvacion()) {
                hechizoExistente.setTirada_salvacion(hechizoEntity.isTirada_salvacion());
            }
            if (hechizoEntity.getHabilidad_tirada_salvacion() != null) {
                hechizoExistente.setHabilidad_tirada_salvacion(hechizoEntity.getHabilidad_tirada_salvacion());
            }
            if (hechizoEntity.isAtaque()) {
                hechizoExistente.setAtaque(hechizoEntity.isAtaque());
            }
            if (hechizoEntity.getDanio() != null) {
                hechizoExistente.setDanio(hechizoEntity.getDanio());
            }
            if (hechizoEntity.getDamageTypes() != null) {
                hechizoExistente.setDamageTypes(hechizoEntity.getDamageTypes());
            }
            if (hechizoEntity.getEs_ritual() != null) {
                hechizoExistente.setEs_ritual(hechizoEntity.getEs_ritual());
            }
            // Aquí puedes agregar el manejo para la escuela de magia si es necesario
            if (hechizoEntity.getEscuelaMagia() != null) {
                hechizoExistente.setEscuelaMagia(hechizoEntity.getEscuelaMagia());
            }
            return hechizoRepository.save(hechizoExistente);
        }).orElseThrow(() -> new EntityNotFoundException("La escuela ingresada no existe"));

    }

    @Override
    public void delete(Long id) {
        hechizoRepository.deleteById(id);
    }

    @Override
    public Page<HechizoEntity> findHechizosByEscuelaId(Long idEscuela, Pageable pageable) {
        return hechizoRepository.findHechizosByEscuelaId(idEscuela, pageable);
    }

    @Override
    public Page<HechizoEntity> findByNivelHechizo(Integer nivelHechizo, Pageable pageable) {
        return hechizoRepository.findByNivelHechizo(nivelHechizo, pageable);
    }

    @Override
    public Page<HechizoEntity> findByDamageTypes(DamageTypes damageTypes, Pageable pageable) {
        return hechizoRepository.findByDamageTypesContaining(damageTypes, pageable);
    }
}
