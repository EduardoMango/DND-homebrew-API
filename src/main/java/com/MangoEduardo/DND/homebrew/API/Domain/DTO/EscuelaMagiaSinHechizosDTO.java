package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EscuelaMagiaSinHechizosDTO {

    private Integer id_escuela;
    private String nombre_escuela;
    private String descripcion_escuela;

}
