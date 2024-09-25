package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
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
    public Optional<HechizoEntity> findById(Integer id) {
        return hechizoRepository.findById(id);
    }

    @Override
    public HechizoEntity save(HechizoEntity hechizoEntity) {
        return hechizoRepository.save(hechizoEntity);
    }

    @Override
    public boolean isExist(Integer id) {
        return hechizoRepository.existsById(id);
    }

    @Override
    public HechizoEntity update(Integer id, HechizoEntity hechizoEntity) {

        hechizoEntity.setId_hechizo(id);

        return hechizoRepository.findById(id).map(hechizoExistente -> {
            // Actualiza solo los campos que no son nulos
            if(hechizoEntity.getNombre_hechizo() != null){
                hechizoExistente.setNombre_hechizo(hechizoEntity.getNombre_hechizo());
            }
            if (hechizoEntity.getNivel_hechizo() != null) {
                hechizoExistente.setNivel_hechizo(hechizoEntity.getNivel_hechizo());
            }
            if (hechizoEntity.getDescripcion_hechizo() != null) {
                hechizoExistente.setDescripcion_hechizo(hechizoEntity.getDescripcion_hechizo());
            } //Repetir por cada atributo
            if (hechizoEntity.getNiveles_mayores()!=null) {
                hechizoExistente.setNiveles_mayores(hechizoEntity.getNiveles_mayores());
            }
            if (hechizoEntity.getTiempo_casteo() != null) {
                hechizoExistente.setTiempo_casteo(hechizoEntity.getTiempo_casteo());
            }
            if (hechizoEntity.getDuracionHechizo() != null) {
                hechizoExistente.setDuracionHechizo(hechizoEntity.getDuracionHechizo());
            }
            if (hechizoEntity.getRango_area() != null){
                hechizoExistente.setRango_area(hechizoEntity.getRango_area());
            }
            if (hechizoEntity.getComponentes()!=null){
                hechizoExistente.setComponentes(hechizoEntity.getComponentes());
            }
            if (hechizoEntity.getAtaque_salvacion()!=null){
                hechizoExistente.setAtaque_salvacion(hechizoEntity.getAtaque_salvacion());
            }
            if (hechizoEntity.getDamage_efecto()!=null){
                hechizoExistente.setDamage_efecto(hechizoEntity.getDamage_efecto());
            }
            if (hechizoEntity.getEs_ritual()!=null){
                hechizoExistente.setEs_ritual(hechizoEntity.getEs_ritual());
            }
            return hechizoRepository.save(hechizoExistente);
        }).orElseThrow(() -> new EntityNotFoundException("La escuela ingresada no existe"));

    }

    @Override
    public void delete(Integer id) {
        hechizoRepository.deleteById(id);
    }
}
