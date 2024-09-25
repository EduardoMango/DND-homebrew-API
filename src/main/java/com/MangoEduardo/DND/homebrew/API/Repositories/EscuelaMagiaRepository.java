package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscuelaMagiaRepository extends CrudRepository<EscuelaMagiaEntity, Integer>
        , PagingAndSortingRepository<EscuelaMagiaEntity, Integer> {
}
