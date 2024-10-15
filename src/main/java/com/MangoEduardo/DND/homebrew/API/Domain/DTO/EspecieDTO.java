package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import com.MangoEduardo.DND.homebrew.API.Domain.Models.RasgoModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/** DTO for {@link com.MangoEduardo.DND.homebrew.API.Domain.Entities.EspecieEntity} */
public class EspecieDTO {

    private Long idEspecie;
    private String nombreEspecie;
    private String descripcionEspecie;

    private List<RasgoModel> rasgos;

    //Solucionar bucle infinito en el get con un DTO sin relaciones
}
