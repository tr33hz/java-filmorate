package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
public class UserController {

    private InMemoryUserStorage inMemoryUserStorage;

    public UserController() {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public User createUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.createUser(user);
    }

    @PutMapping ("/users")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public User updateUser(@Valid @RequestBody User user) {
        return inMemoryUserStorage.updateUser(user);
    }
}
