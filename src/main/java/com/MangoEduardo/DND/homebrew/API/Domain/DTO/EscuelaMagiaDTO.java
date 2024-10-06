package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscuelaMagiaDTO {

    @JsonView(Views.Public.class)
    private Integer id_escuela;
    @JsonView(Views.Public.class)
    private String nombre_escuela;
    @JsonView(Views.Public.class)
    private String descripcion_escuela;
    @JsonView(Views.Public.class)
    private List<HechizoDTO> hechizos;
}
