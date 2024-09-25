package com.MangoEduardo.DND.homebrew.API.Mappers.Impl;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HechizoMapperImpl implements IMapper<HechizoEntity, HechizoDTO> {

    private final ModelMapper modelMapper;

    public HechizoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public HechizoDTO mapTo(HechizoEntity hechizoEntity) {
        return modelMapper.map(hechizoEntity, HechizoDTO.class);
    }

    @Override
    public HechizoEntity mapFrom(HechizoDTO hechizoDTO) {
        return modelMapper.map(hechizoDTO, HechizoEntity.class);
    }
}
