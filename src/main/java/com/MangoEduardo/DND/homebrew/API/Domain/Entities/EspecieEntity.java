package com.MangoEduardo.DND.homebrew.API.Domain.Entities;

import com.MangoEduardo.DND.homebrew.API.Domain.Models.RasgoModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "especie")
public class EspecieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecie;
    private String nombreEspecie;
    private String descripcionEspecie;
    private Boolean estaBorrado;

    @ElementCollection
    @CollectionTable(
            name="rasgos",
            joinColumns=@JoinColumn(name="id_especie")
    )
    private List<RasgoModel> rasgos;

    @PrePersist
    public void prePersist() {
        estaBorrado = false;
    }
}
