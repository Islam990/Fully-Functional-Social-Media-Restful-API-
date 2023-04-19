package com.islamgad.restapi.restapi.controllers;

import com.islamgad.restapi.restapi.Exceptions.NotFoundUserException;
import com.islamgad.restapi.restapi.models.Post;
import com.islamgad.restapi.restapi.models.User;
import com.islamgad.restapi.restapi.services.PostRepository;
import com.islamgad.restapi.restapi.services.UserRepo;
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
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    private UserRepo userRepo;
    private PostRepository postRepository;
    private final MessageSource messageSource;

    public UserController(UserService userService, UserRepo userRepo, PostRepository postRepository, MessageSource messageSource) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.postRepository = postRepository;
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
        User savedUser = userRepo.save(user);

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


    @GetMapping("/users/{id}/posts")
    public List<Post> getAllPosts(@PathVariable int id) {
        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty())
            throw new NotFoundUserException("User Not Found");


        return user.get().getPostList();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> savePost(@PathVariable int id, @Valid @RequestBody Post post) {

        Optional<User> user = userRepo.findById(id);

        if (user.isEmpty())
            throw new NotFoundUserException("User Not Found");

        post.setUser(user.get());

        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


}

// 2005/2022/01/020  3227/2022/01/020