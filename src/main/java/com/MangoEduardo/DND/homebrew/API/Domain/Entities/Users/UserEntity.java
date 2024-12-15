package com.MangoEduardo.DND.homebrew.API.Domain.Entities.Users;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity()
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false)
    private String email;
    @Column(unique=true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private Boolean isDeleted;

    @PrePersist
    public void prePersist() {
        isDeleted = false;
    }
}
