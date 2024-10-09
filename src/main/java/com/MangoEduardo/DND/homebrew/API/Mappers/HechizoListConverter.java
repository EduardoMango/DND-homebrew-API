package com.MangoEduardo.DND.homebrew.API.Mappers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EscuelaMagiaSinHechizosDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import org.modelmapper.AbstractConverter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class HechizoListConverter extends AbstractConverter<List<HechizoEntity>, List<HechizoDTO>> {

    @Override
    protected List<HechizoDTO> convert(List<HechizoEntity> hechizoEntityList) {
        if (hechizoEntityList!=null) {
            return hechizoEntityList.stream()
                    .filter(Objects::nonNull) // Ignorar entidades nulas
                    .map(hechizoEntity -> {
                        EscuelaMagiaSinHechizosDTO escuelaMagiaDTO = hechizoEntity.getEscuelaMagia() != null ?
                                new EscuelaMagiaSinHechizosDTO(hechizoEntity.getEscuelaMagia().getId_escuela(),
                                        hechizoEntity.getEscuelaMagia().getNombre_escuela(),
                                        hechizoEntity.getEscuelaMagia().getDescripcion_escuela()) : null;

                        return new HechizoDTO(
                                hechizoEntity.getId_hechizo(),
                                hechizoEntity.getNombreHechizo(),
                                hechizoEntity.getNivel_hechizo(),
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
                                hechizoEntity.isTirada_salvacion(),
                                hechizoEntity.getHabilidad_tirada_salvacion(),
                                hechizoEntity.isAtaque(),
                                hechizoEntity.getDanio(),
                                hechizoEntity.getDanio_Tipo(),
                                hechizoEntity.getEs_ritual(),
                                escuelaMagiaDTO
                        );
                    })
                    .toList();
        }else{
            return Collections.emptyList();
        }

    }
}