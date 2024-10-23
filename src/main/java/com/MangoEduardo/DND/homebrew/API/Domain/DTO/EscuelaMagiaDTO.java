package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscuelaMagiaDTO implements Serializable {

    @JsonView(Views.Public.class)
    private Long id_escuela;
    @JsonView(Views.Public.class)
    @NotBlank(message = "El nombre de la escuela no puede estar en blanco")
    private String nombreEscuela;
    @JsonView(Views.Public.class)
    @NotBlank(message = "La descripción de la escuela no puede estar en blanco")
    private String descripcion_escuela;

    @JsonView(Views.Internal.class)
    private List<HechizoDTO> hechizos;
}
