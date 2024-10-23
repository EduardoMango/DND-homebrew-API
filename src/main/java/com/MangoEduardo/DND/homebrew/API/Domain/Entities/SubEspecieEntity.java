package com.MangoEduardo.DND.homebrew.API.Domain.Entities;


import com.MangoEduardo.DND.homebrew.API.Domain.Models.RasgoModel;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "subespecies")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SubEspecieEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSubespecie;

    private String nombreSubespecie;

    @Lob
    private String descripcionSubespecie;

    private Boolean estaBorrado;

    @ElementCollection
    @CollectionTable(
            name="rasgos_subespecies",
            joinColumns=@JoinColumn(name="id_subespecie")
    )
    private List<RasgoModel> rasgos;

    @ManyToOne
    @JoinColumn(name = "id_especie")
    private EspecieEntity especie;

    @PrePersist
    public void prePersist() {
        estaBorrado = false;
    }
}
