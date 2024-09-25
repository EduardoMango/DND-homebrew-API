package com.MangoEduardo.DND.homebrew.API.Mappers;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.EscuelaMagiaSinHechizosDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.HechizoDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.HechizoEntity;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.Objects;

public class HechizoListConverter extends AbstractConverter<List<HechizoEntity>, List<HechizoDTO>> {

    @Override
    protected List<HechizoDTO> convert(List<HechizoEntity> hechizoEntityList) {
        return hechizoEntityList.stream()
                .filter(Objects::nonNull) // Ignorar entidades nulas
                .map(hechizoEntity -> {
                    EscuelaMagiaSinHechizosDTO escuelaMagiaDTO = hechizoEntity.getEscuelaMagia() != null ?
                            new EscuelaMagiaSinHechizosDTO(hechizoEntity.getEscuelaMagia().getId_escuela(),
                                    hechizoEntity.getEscuelaMagia().getNombre_escuela(),
                                    hechizoEntity.getEscuelaMagia().getDescripcion_escuela()) : null;

                    return new HechizoDTO(
                            hechizoEntity.getId_hechizo(),
                            hechizoEntity.getNombre_hechizo(),
                            hechizoEntity.getNivel_hechizo(),
                            hechizoEntity.getDescripcion_hechizo(),
                            hechizoEntity.getNiveles_mayores(),
                            hechizoEntity.getTiempo_casteo(),
                            hechizoEntity.getDuracionHechizo(),
                            hechizoEntity.getRango_area(),
                            hechizoEntity.getComponentes(),
                            hechizoEntity.getAtaque_salvacion(),
                            hechizoEntity.getDamage_efecto(),
                            hechizoEntity.getEs_ritual(),
                            escuelaMagiaDTO
                    );
                })
                .toList();
    }
}