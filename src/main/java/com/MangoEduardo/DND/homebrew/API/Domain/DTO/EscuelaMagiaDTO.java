package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.fasterxml.jackson.annotation.JsonView;
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
    private String nombreEscuela;
    @JsonView(Views.Public.class)
    private String descripcion_escuela;
    @JsonView(Views.Public.class)
    private Boolean estaBorrado;
    @JsonView(Views.Internal.class)
    private List<HechizoDTO> hechizos;
}
