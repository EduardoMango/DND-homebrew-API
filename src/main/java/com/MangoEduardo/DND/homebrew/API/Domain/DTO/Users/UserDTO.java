package com.MangoEduardo.DND.homebrew.API.Domain.DTO.Users;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    @JsonView(Views.Public.class)
    private Long id;
    @Email (message = "El correo no es válido",
            regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
            //Estandar RFC 5322 para validar emails
    @NotBlank(message = "El correo no puede estar en blanco")
    @JsonView(Views.Public.class)
    private String email;
    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @JsonView(Views.Public.class)
    private String username;
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @JsonView(Views.Internal.class)
    private String password;
}
