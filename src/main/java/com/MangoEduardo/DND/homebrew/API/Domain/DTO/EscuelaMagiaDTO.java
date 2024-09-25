package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

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

    private Integer id_escuela;
    private String nombre_escuela;
    private String descripcion_escuela;

    private List<HechizoDTO> hechizos;
}
