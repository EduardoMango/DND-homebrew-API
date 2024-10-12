package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.MangoEduardo.DND.homebrew.API.Domain.Models.DamageTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
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
    private String nombreHechizo;

    @JsonView(Views.Public.class)
    private Integer nivelHechizo;

    @JsonView(Views.Public.class)
    private String descripcion_hechizo;

    @JsonView(Views.Public.class)
    private String niveles_mayores;

    @JsonView(Views.Public.class)
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
    private boolean verbal;

    @JsonView(Views.Public.class)
    private boolean somantico;

    @JsonView(Views.Public.class)
    private boolean material;

    @JsonView(Views.Public.class)
    private String material_requerido;

    @JsonView(Views.Public.class)
    private String material_costo;

    @JsonView(Views.Public.class)
    private boolean concentracion;

    @JsonView(Views.Public.class)
    private boolean tirada_salvacion;

    @JsonView(Views.Public.class)
    private String habilidad_tirada_salvacion;

    @JsonView(Views.Public.class)
    private boolean ataque;

    @JsonView(Views.Public.class)
    private String danio;

    @JsonView(Views.Public.class)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
    private List<DamageTypes> damageTypes;

    @JsonView(Views.Public.class)
    private Boolean es_ritual;

    @JsonView(Views.Internal.class)
    private EscuelaMagiaSinHechizosDTO escuelaMagia;
}
