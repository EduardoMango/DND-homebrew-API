package com.MangoEduardo.DND.homebrew.API.Domain.DTO.Resources;

import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources.EspecieEntity;
import com.MangoEduardo.DND.homebrew.API.Domain.Models.RasgoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

/** DTO for {@link EspecieEntity} */
public class EspecieDTO {

    private Long idEspecie;
    @NotBlank(message = "El nombre de la especie no puede estar en blanco")
    private String nombreEspecie;
    @NotBlank(message = "La descripción de la especie no puede estar en blanco")
    private String descripcionEspecie;

    @NotNull(message = "Los rasgos de la especie no pueden ser nulos")
    private List<RasgoModel> rasgos;
    private List <SubEspecieDTO> subespecies;
}
