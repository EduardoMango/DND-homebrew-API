package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EscuelaMagiaSinHechizosDTO implements Serializable {

    private Long id_escuela;
    private String nombreEscuela;
    private String descripcion_escuela;

}
