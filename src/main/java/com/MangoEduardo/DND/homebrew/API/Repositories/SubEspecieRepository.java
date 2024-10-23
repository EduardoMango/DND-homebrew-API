package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.SubEspecieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SubEspecieRepository extends JpaRepository<SubEspecieEntity, Long> {

    Page<SubEspecieEntity> findByEspecie(EspecieEntity especie, Pageable pageable);
}
