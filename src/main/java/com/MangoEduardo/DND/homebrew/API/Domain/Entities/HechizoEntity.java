package com.MangoEduardo.DND.homebrew.API.Domain.Entities;

import com.MangoEduardo.DND.homebrew.API.Domain.Models.DamageTypes;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "hechizos")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HechizoEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hechizo;
    private String nombreHechizo;
    private Integer nivelHechizo;
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
    private boolean concentracion; //Filtrar?
    private boolean tirada_salvacion; //Filtrar
    private String habilidad_tirada_salvacion;
    private boolean ataque; //Filtrar?
    private String danio;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DamageTypes> damageTypes; 
    private Boolean es_ritual; //Filtrar?

    private Boolean estaBorrado;

    @ManyToOne
    @JoinColumn(name = "id_escuela")
    private EscuelaMagiaEntity escuelaMagia;

    @PrePersist
    public void prePersist() {
        estaBorrado = false;
    }
}
