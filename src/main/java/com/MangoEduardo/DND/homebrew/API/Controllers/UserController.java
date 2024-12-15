package com.MangoEduardo.DND.homebrew.API.Controllers;

import com.MangoEduardo.DND.homebrew.API.Config.Views;
import com.MangoEduardo.DND.homebrew.API.Domain.DTO.Users.UserDTO;
import com.MangoEduardo.DND.homebrew.API.Services.Interfaces.IUserService;
import com.fasterxml.jackson.annotation.JsonView;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;
    private final PagedResourcesAssembler<UserDTO> pagedResourcesAssembler;
    private final Dotenv dotenv;

    public UserController(IUserService userService, PagedResourcesAssembler<UserDTO> pagedResourcesAssembler, Dotenv dotenv) {
        this.userService = userService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.dotenv = dotenv;
    }

    @JsonView(Views.Public.class)
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> getUsers(Pageable pageable) {

        System.out.println(  dotenv.get("SENDGRID_API_KEY"));

        Page<UserDTO> page = userService.findAll(pageable);
        PagedModel<EntityModel<UserDTO>> pagedModel = pagedResourcesAssembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    @JsonView(Views.Public.class)
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    @JsonView(Views.Public.class)
    public ResponseEntity<UserDTO> postUser(@RequestBody @Valid UserDTO userDTO) {

        UserDTO saved = userService.save(userDTO);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<UserDTO> putUser(@PathVariable Long id,
                                           @RequestBody @Valid UserDTO userDTO) {
        userDTO.setId(id);
        UserDTO saved = userService.update(id, userDTO);
        return ResponseEntity.ok(saved);

    }

    @DeleteMapping("/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
