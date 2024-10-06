package com.MangoEduardo.DND.homebrew.API.Domain.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "hechizos")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HechizoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_hechizo;
    private String nombre_hechizo;
    private Integer nivel_hechizo;
    @Lob
    private String descripcion_hechizo;
    private String niveles_mayores;
    private String tiempo_casteo;
    private String duracion_Hechizo;
    private String rango_texto;
    private Integer rango;
    private String area;
    private String tipo_objetivo;
    private boolean verbal;
    private boolean somantico;
    private boolean material;
    private String material_requerido;
    private String material_costo;
    private boolean concentracion;
    private boolean tirada_salvacion;
    private String habilidad_tirada_salvacion;
    private boolean ataque;
    private String danio;
    private String danio_Tipo;
    private Boolean es_ritual;


    @ManyToOne
    @JoinColumn(name = "id_escuela")
    private EscuelaMagiaEntity escuelaMagia;

}
