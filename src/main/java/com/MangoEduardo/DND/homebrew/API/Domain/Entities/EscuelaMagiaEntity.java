package com.MangoEduardo.DND.homebrew.API.Domain.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "escuelas_magia")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EscuelaMagiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_escuela;
    private String nombre_escuela;
    @Lob
    private String descripcion_escuela;

    @OneToMany(mappedBy = "escuelaMagia", cascade = CascadeType.ALL)
    private List<HechizoEntity> hechizos;

}
