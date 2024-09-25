package com.MangoEduardo.DND.homebrew.API.Repositories;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HechizoRepository extends CrudRepository<HechizoEntity, Integer>,
        PagingAndSortingRepository<HechizoEntity, Integer> {
}
