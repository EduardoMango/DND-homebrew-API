package com.MangoEduardo.DND.homebrew.API.Mappers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EscuelaMagiaSinHechizosDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HechizoListConverter implements ConditionalConverter<List<HechizoEntity>, List<HechizoDTO>> {

//    @Override
//    protected List<HechizoDTO> convert(List<HechizoEntity> hechizoEntityList) {
//        if (hechizoEntityList!=null) {
//            try {
//            return hechizoEntityList.stream()
//                    .filter(Objects::nonNull) // Ignorar entidades nulas
//                    .map(hechizoEntity -> {
//                        EscuelaMagiaSinHechizosDTO escuelaMagiaDTO = hechizoEntity.getEscuelaMagia() != null ?
//                                new EscuelaMagiaSinHechizosDTO(hechizoEntity.getEscuelaMagia().getId_escuela(),
//                                        hechizoEntity.getEscuelaMagia().getNombreEscuela(),
//                                        hechizoEntity.getEscuelaMagia().getDescripcion_escuela()) : null;
//
//                        return new HechizoDTO(
//                                hechizoEntity.getId_hechizo(),
//                                hechizoEntity.getNombreHechizo(),
//                                hechizoEntity.getNivelHechizo(),
//                                hechizoEntity.getDescripcion_hechizo(),
//                                hechizoEntity.getNiveles_mayores(),
//                                hechizoEntity.getTiempo_casteo(),
//                                hechizoEntity.getDuracion_Hechizo(),
//                                hechizoEntity.getRango_texto(),
//                                hechizoEntity.getRango(),
//                                hechizoEntity.getArea(),
//                                hechizoEntity.getTipo_objetivo(),
//                                hechizoEntity.isVerbal(),
//                                hechizoEntity.isSomantico(),
//                                hechizoEntity.isMaterial(),
//                                hechizoEntity.getMaterial_requerido(),
//                                hechizoEntity.getMaterial_costo(),
//                                hechizoEntity.isConcentracion(),
//                                hechizoEntity.isTirada_salvacion(),
//                                hechizoEntity.getHabilidad_tirada_salvacion(),
//                                hechizoEntity.isAtaque(),
//                                hechizoEntity.getDanio(),
//                                hechizoEntity.getDamageTypes(),
//                                hechizoEntity.getEs_ritual(),
//                                escuelaMagiaDTO
//                        );
//                    })
//                    .toList();
//        } catch (ClassCastException e) {
//            System.err.println("Error de conversión: " + e.getMessage());
//            e.printStackTrace();
//            throw e; // o manejar de otra manera
//        }
//        }else{
//            return Collections.emptyList();
//        }
//
//    }

    @Override
    public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
        if (List.class.isAssignableFrom(sourceType) && List.class.isAssignableFrom(destinationType)) {
            return MatchResult.FULL;
        }
        return MatchResult.NONE;
    }

    @Override
    public List<HechizoDTO> convert(MappingContext<List<HechizoEntity>, List<HechizoDTO>> mappingContext) {
        // El contexto contiene la lista de origen (HechizoEntity) y se mapea a la lista de destino (HechizoDTO)
        List<HechizoEntity> hechizoEntityList = mappingContext.getSource();

        if (hechizoEntityList != null) {
            try {
                return hechizoEntityList.stream()
                        .filter(Objects::nonNull) // Ignorar entidades nulas
                        .map(hechizoEntity -> {
                            // Mapea la EscuelaMagia a EscuelaMagiaSinHechizosDTO
                            EscuelaMagiaSinHechizosDTO escuelaMagiaDTO = hechizoEntity.getEscuelaMagia() != null ?
                                    new EscuelaMagiaSinHechizosDTO(
                                            hechizoEntity.getEscuelaMagia().getId_escuela(),
                                            hechizoEntity.getEscuelaMagia().getNombreEscuela(),
                                            hechizoEntity.getEscuelaMagia().getDescripcion_escuela(),
                                            hechizoEntity.getEscuelaMagia().getEstaBorrado()
                                    ) : null;

                            // Mapea HechizoEntity a HechizoDTO
                            return new HechizoDTO(
                                    hechizoEntity.getId_hechizo(),
                                    hechizoEntity.getNombreHechizo(),
                                    hechizoEntity.getNivelHechizo(),
                                    hechizoEntity.getDescripcion_hechizo(),
                                    hechizoEntity.getNiveles_mayores(),
                                    hechizoEntity.getTiempo_casteo(),
                                    hechizoEntity.getDuracion_Hechizo(),
                                    hechizoEntity.getRango_texto(),
                                    hechizoEntity.getRango(),
                                    hechizoEntity.getArea(),
                                    hechizoEntity.getTipo_objetivo(),
                                    hechizoEntity.isVerbal(),
                                    hechizoEntity.isSomantico(),
                                    hechizoEntity.isMaterial(),
                                    hechizoEntity.getMaterial_requerido(),
                                    hechizoEntity.getMaterial_costo(),
                                    hechizoEntity.isConcentracion(),
                                    hechizoEntity.isTiradaSalvacion(),
                                    hechizoEntity.getHabilidad_tirada_salvacion(),
                                    hechizoEntity.isEsAtaque(),
                                    hechizoEntity.getDanio(),
                                    hechizoEntity.getDamageTypes(),
                                    hechizoEntity.getEsRitual(),
                                    escuelaMagiaDTO
                            );
                        })
                        .collect(Collectors.toList());
            } catch (ClassCastException e) {
                System.err.println("Error de conversión: " + e.getMessage());
                e.printStackTrace();
                throw e; // Lanza la excepción o maneja de otra manera
            }
        } else {
            return Collections.emptyList(); // Retorna una lista vacía si la fuente es nula
        }
    }
}
