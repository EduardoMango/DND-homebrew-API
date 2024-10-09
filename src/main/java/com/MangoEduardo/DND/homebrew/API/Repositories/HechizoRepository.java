package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface HechizoRepository extends CrudRepository<HechizoEntity, Integer>,PagingAndSortingRepository<HechizoEntity, Integer> {

    Page<HechizoEntity> findByNombreHechizoContainingIgnoreCase(String nombre_hechizo, Pageable pageable);

    @Query("SELECT h FROM HechizoEntity h WHERE h.escuelaMagia.id_escuela = :id_escuela AND h.estaBorrado = false")
    Page<HechizoEntity> findHechizosByEscuelaId(@Param("id_escuela") Integer idEscuela, Pageable pageable);

}
