package com.MangoEduardo.DND.homebrew.API.Services.Interfaces;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Users.UserDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUserService {

    Page<UserDTO> findAll(Pageable pageable);
    UserDTO findById(Long id);
    UserDTO save(UserDTO user);
    boolean isExist(Long id);
    UserDTO update(Long id, UserDTO user);
    void delete(Long id);

}
