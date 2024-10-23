package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.MangoEduardo.DND.homebrew.API.Domain.Enums.DamageTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HechizoDTO implements Serializable {

    @JsonView(Views.Public.class)
    private Long id_hechizo;

    @JsonView(Views.Public.class)
    @NotBlank(message = "El nombre del hechizo no puede estar en blanco")
    private String nombreHechizo;

    @JsonView(Views.Public.class)
    @NotNull(message = "El nivel del hechizo no puede ser nulo")
    @Min(value = 0, message = "El nivel del hechizo debe ser al menos 0")
    @Max(value = 9, message = "El nivel del hechizo no puede ser mayor a 9  ")
    private Integer nivelHechizo;

    @JsonView(Views.Public.class)
    @NotBlank(message = "La descripción del hechizo no puede estar en blanco")
    private String descripcion_hechizo;

    @JsonView(Views.Public.class)
    private String niveles_mayores;

    @JsonView(Views.Public.class)
    @NotBlank(message = "El tiempo de casteo no puede estar en blanco")
    private String tiempo_casteo;

    @JsonView(Views.Public.class)
    private String duracion_Hechizo;

    @JsonView(Views.Public.class)
    private String rango_texto;

    @JsonView(Views.Public.class)
    private Integer rango;

    @JsonView(Views.Public.class)
    private String area;

    @JsonView(Views.Public.class)
    private String tipo_objetivo;

    @JsonView(Views.Public.class)
    @NotNull(message = "El campo verbal no puede ser nulo")
    private boolean verbal;

    @JsonView(Views.Public.class)
    @NotNull(message = "El campo somantico no puede ser nulo")
    private boolean somantico;

    @JsonView(Views.Public.class)
    @NotNull(message = "El campo material no puede ser nulo")
    private boolean material;

    @JsonView(Views.Public.class)
    private String material_requerido;

    @JsonView(Views.Public.class)
    private String material_costo;

    @JsonView(Views.Public.class)
    @NotNull(message = "El campo concentracion no puede ser nulo")
    private boolean concentracion;

    @JsonView(Views.Public.class)
    @NotNull(message = "El campo tirada de salvacion no puede ser nulo")
    private boolean tiradaSalvacion;

    @JsonView(Views.Public.class)
    private String habilidad_tirada_salvacion;

    @JsonView(Views.Public.class)
    @NotNull(message = "El campo esAtaque no puede ser nulo")
    private boolean esAtaque;

    @JsonView(Views.Public.class)
    private String danio;

    @JsonView(Views.Public.class)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    private List<DamageTypes> damageTypes;

    @JsonView(Views.Public.class)
    @NotNull(message = "El campo esRitual no puede ser nulo")
    private Boolean esRitual;

    @JsonView(Views.Internal.class)
    @NotNull(message = "El campo escuelaMagia no puede ser nulo")
    private EscuelaMagiaSinHechizosDTO escuelaMagia;
}
