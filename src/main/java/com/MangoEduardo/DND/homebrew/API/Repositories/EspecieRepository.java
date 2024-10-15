package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecieRepository extends JpaRepository<EspecieEntity, Long> {


    Page<EspecieEntity> findByNombreEspecieContainingIgnoreCase(String nombreEspecie, Pageable pageable);
}
