package com.islamgad.restapi.restapi.controllers;

import com.islamgad.restapi.restapi.Exceptions.NotFoundUserException;
import com.islamgad.restapi.restapi.models.User;
import com.islamgad.restapi.restapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);

        if (user == null)
            throw new NotFoundUserException("id: " + id);

        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        entityModel.add(webMvcLinkBuilder.withRel("Get-All_users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/testI18n")
    public String testI18n() {
        Locale locale = LocaleContextHolder.getLocale();
        final String message = messageSource.getMessage("welcome", null, "Default MSG", locale);
        System.out.println("message = " + message);
        return message;
    }
}