package com.MangoEduardo.DND.homebrew.API.Mappers.Impl;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EspecieMapperImpl implements IMapper<EspecieEntity, EspecieDTO> {

    private final ModelMapper modelMapper;

    public EspecieMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public EspecieDTO mapTo(EspecieEntity especieEntity) {
        return modelMapper.map(especieEntity, EspecieDTO.class);
    }

    @Override
    public EspecieEntity mapFrom(EspecieDTO especieDTO) {
        return modelMapper.map(especieDTO, EspecieEntity.class);
    }
}
