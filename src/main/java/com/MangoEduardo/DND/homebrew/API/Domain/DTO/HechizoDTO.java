package com.MangoEduardo.DND.homebrew.API.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HechizoDTO {

    private Integer id_hechizo;
    private String nombre_hechizo;
    private Integer nivel_hechizo;
    private String descripcion_hechizo;
    private String niveles_mayores;
    private String tiempo_casteo;
    private String duracionHechizo;
    private String rango_area;
    private String componentes;
    private String ataque_salvacion;
    private String damage_efecto;
    private Boolean es_ritual;

    private EscuelaMagiaSinHechizosDTO escuelaMagia;
}
