package com.MangoEduardo.DND.homebrew.API.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "escuelas_magia")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EscuelaMagiaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_escuela;
    private String nombreEscuela;
    @Lob
    private String descripcion_escuela;

    private Boolean estaBorrado;

    @OneToMany(mappedBy = "escuelaMagia", cascade = CascadeType.ALL)
    private List<HechizoEntity> hechizos;

    @PrePersist
    public void prePersist() {
        estaBorrado = false;
    }
}
