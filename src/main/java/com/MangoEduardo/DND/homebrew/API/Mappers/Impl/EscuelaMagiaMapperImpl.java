package com.MangoEduardo.DND.homebrew.API.Mappers.Impl;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EscuelaMagiaDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EscuelaMagiaMapperImpl implements IMapper<EscuelaMagiaEntity, EscuelaMagiaDTO> {

    private final ModelMapper modelMapper;

    public EscuelaMagiaMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
    }

    @Override
    public EscuelaMagiaDTO mapTo(EscuelaMagiaEntity escuelaMagiaEntity) {
        EscuelaMagiaDTO escuelaMagiaDTO =  modelMapper.map(escuelaMagiaEntity, EscuelaMagiaDTO.class);

        if (escuelaMagiaEntity.getHechizos() != null){
            escuelaMagiaDTO.setHechizos(modelMapper.map(escuelaMagiaEntity.getHechizos(), new TypeToken<List<HechizoDTO>>(){}.getType()));
        }
        return escuelaMagiaDTO;
    }

    @Override
    public EscuelaMagiaEntity mapFrom(EscuelaMagiaDTO escuelaMagiaDTO) {
        return modelMapper.map(escuelaMagiaDTO, EscuelaMagiaEntity.class);
    }
}
