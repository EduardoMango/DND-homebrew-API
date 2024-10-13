package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Models.DamageTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface HechizoRepository extends JpaRepository<HechizoEntity, Long> {

    Page<HechizoEntity> findByNombreHechizoContainingIgnoreCase(String nombre_hechizo, Pageable pageable);
    Page<HechizoEntity> findByNivelHechizo(Integer nivelHechizo, Pageable pageable);

    Page<HechizoEntity> findByDamageTypesContaining(DamageTypes damageTypes, Pageable pageable);

    @Query("SELECT h FROM HechizoEntity h WHERE h.escuelaMagia.id_escuela = :id_escuela AND h.estaBorrado = false")
    Page<HechizoEntity> findHechizosByEscuelaId(@Param("id_escuela") Long idEscuela, Pageable pageable);
}
