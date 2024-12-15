package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscuelaMagiaRepository extends JpaRepository<EscuelaMagiaEntity, Long> {

    Page<EscuelaMagiaEntity> findByNombreEscuelaContainingIgnoreCase(String nombreEscuela, Pageable pageable);


}
