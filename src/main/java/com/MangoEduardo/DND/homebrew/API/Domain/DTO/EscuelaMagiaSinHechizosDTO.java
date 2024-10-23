package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EscuelaMagiaSinHechizosDTO implements Serializable {

    private Long id_escuela;
    @NotBlank(message = "El nombre de la escuela no puede estar en blanco")
    private String nombreEscuela;
    @NotBlank(message = "La descripción de la escuela no puede estar en blanco")
    private String descripcion_escuela;

}
