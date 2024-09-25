package com.MangoEduardo.DND.homebrew.API.Domain.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Reference;

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
    private String descripcion_hechizo;
    private String niveles_mayores;
    private String tiempo_casteo;
    private String duracionHechizo;
    private String rango_area;
    private String componentes;
    private String ataque_salvacion;
    private String damage_efecto;
    private Boolean es_ritual;


    @ManyToOne
    @JoinColumn(name = "id_escuela")
    private EscuelaMagiaEntity escuelaMagia;

}
