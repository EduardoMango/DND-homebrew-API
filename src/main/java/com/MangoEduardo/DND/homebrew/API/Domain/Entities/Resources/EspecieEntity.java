package com.MangoEduardo.DND.homebrew.API.Domain.Entities.Resources;

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
@Table(name = "especies")
public class EspecieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEspecie;

    private String nombreEspecie;
    @Lob
    private String descripcionEspecie;
    private Boolean estaBorrado;

    @ElementCollection
    @CollectionTable(
            name="rasgos_especies",
            joinColumns=@JoinColumn(name="id_especie")
    )
    private List<RasgoModel> rasgos;


    @OneToMany(mappedBy = "especie", cascade = CascadeType.ALL)
    private List<SubEspecieEntity> subespecies;

    @PrePersist
    public void prePersist() {
        estaBorrado = false;
    }
}
