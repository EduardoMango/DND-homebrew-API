package com.MangoEduardo.DND.homebrew.API.Mappers.Impl;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.SubEspecieDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.SubEspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SubEspecieMapperImpl implements IMapper<SubEspecieEntity, SubEspecieDTO> {

    private final ModelMapper modelMapper;

    public SubEspecieMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SubEspecieDTO mapTo(SubEspecieEntity subEspecieEntity) {
        return modelMapper.map(subEspecieEntity, SubEspecieDTO.class);
    }

    @Override
    public SubEspecieEntity mapFrom(SubEspecieDTO subEspecieDTO) {
        return modelMapper.map(subEspecieDTO, SubEspecieEntity.class);
    }
}
