package com.MangoEduardo.DND.homebrew.API.Config;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EscuelaMagiaDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.EscuelaMagiaEntity;
import com.MangoEduardo.DND.homebrew.API.Mappers.Converters.HechizoListConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    ///Crear bean para que se use cuando se haga falta.
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE); //Para que el mapper trabaje con nested objects

        //Asi se agrega un mapeo especifico para clases.
        modelMapper.typeMap(EscuelaMagiaEntity.class, EscuelaMagiaDTO.class)
                .addMappings(mapper -> mapper.using(new HechizoListConverter())
                        .map(EscuelaMagiaEntity::getHechizos, EscuelaMagiaDTO::setHechizos));

        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        return objectMapper;
    }

}
