package com.MangoEduardo.DND.homebrew.API.Domain.Models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Embeddable
public class RasgoModel {

    private String nombreRasgo;
    private String descripcion;

}
