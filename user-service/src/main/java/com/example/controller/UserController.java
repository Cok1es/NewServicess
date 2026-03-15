package com.example.controller;

import com.example.dto.UserDTO;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Управление пользователями")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public EntityModel<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.findById(id);

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getUser(id)).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
    }

    @GetMapping
    @Operation(summary = "Получить всех пользователей")
    public CollectionModel<EntityModel<UserDTO>> getAllUsers() {
        List<EntityModel<UserDTO>> users = userService.findAll().stream()
                .map(user -> EntityModel.of(user,
                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(users,
                linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public EntityModel<UserDTO> createUser(@RequestBody UserDTO dto) {
        UserDTO createdUser = userService.create(dto);
        return EntityModel.of(createdUser,
                linkTo(methodOn(UserController.class).getUser(createdUser.getId())).withSelfRel());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
