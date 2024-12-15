package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.SubEspecieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubEspecieRepository extends JpaRepository<SubEspecieEntity, Long> {

    Page<SubEspecieEntity> findByEspecie(EspecieEntity especie, Pageable pageable);
}
