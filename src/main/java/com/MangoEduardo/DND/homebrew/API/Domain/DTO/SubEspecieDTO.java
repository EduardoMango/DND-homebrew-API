package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import com.MangoEduardo.DND.homebrew.API.Domain.Models.RasgoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubEspecieDTO {

    private Long idSubespecie;
    @NotBlank(message = "El nombre de la subespecie no puede estar en blanco")
    private String nombreSubespecie;
    @NotBlank(message = "La descripción de la subespecie no puede estar en blanco")
    private String descripcionSubespecie;

    @NotNull(message = "Los rasgos de la subespecie no pueden ser nulos")
    private List<RasgoModel> rasgos;
}
