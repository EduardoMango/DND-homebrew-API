package com.MangoEduardo.DND.homebrew.API.Services.Implementations;

import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Users.UserDTO;
import com.MangoEduardo.DND.homebrew.API.Domain.Entities.Users.UserEntity;
import com.MangoEduardo.DND.homebrew.API.Exceptions.UserNotFoundException;
import com.MangoEduardo.DND.homebrew.API.Mappers.IMapper;
import com.MangoEduardo.DND.homebrew.API.Repositories.UserRepository;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final IMapper<UserEntity, UserDTO> userMapper;

    public UserServiceImpl(UserRepository userRepository, IMapper<UserEntity, UserDTO> userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<UserEntity> page = userRepository.findAll(pageable);

        List<UserDTO> nonDeletedUsers = page.getContent().stream()
                .filter(user -> !Boolean.TRUE.equals(user.getIsDeleted()))
                .map(userMapper::mapTo)
                .toList();

    return new PageImpl<>(nonDeletedUsers, pageable, page.getTotalElements());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        if (user.isPresent() && !Boolean.TRUE.equals(user.get().getIsDeleted())) {
            return userMapper.mapTo(user.get());
        }
        throw new UserNotFoundException(id);
    }

    @Override
    public UserDTO save(UserDTO user) {

        UserEntity saved = userRepository.save(userMapper.mapFrom(user));

        return userMapper.mapTo(saved);
    }

    @Override
    public boolean isExist(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDTO update(Long id, UserDTO user) {

        UserEntity updated = userRepository
                .findById(id)
                .map(toUpdate -> {
                    if (user.getEmail() != null) {
                        toUpdate.setEmail(user.getEmail());
                    }
                    if (user.getUsername() != null) {

                        toUpdate.setUsername(user.getUsername());
                    }
                    if (user.getPassword() != null) {
                        toUpdate.setPassword(user.getPassword());
                    }
                    return userRepository.save(toUpdate);
                })
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.mapTo(updated);
    }

    @Override
    public void delete(Long id) {
        Optional<UserEntity> toBeDeleted = userRepository.findById(id);

        if (toBeDeleted.isPresent()) {
            UserEntity user = toBeDeleted.get();
            user.setIsDeleted(true);
            userRepository.save(user);
        }else {
            throw new UserNotFoundException(id);
        }
    }
}
