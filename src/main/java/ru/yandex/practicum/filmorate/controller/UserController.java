package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private int countId = 0;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        if (users.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(new ArrayList<>(users.values()));
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

        if (user.getName() == null) {
            log.debug("user={} имя изменено на логин", user);
            user.setName(user.getLogin());
        }

        final int id = ++countId;
        user.setId(id);

        users.put(id, user);
        log.debug("Пользователь user={} успешно создан", user);
            return ResponseEntity.ok(user);
    }

    @PutMapping ("/users")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        final int id = user.getId();

        if (!users.containsKey(id)) {
            log.warn("Попытка обновить несуществующего пользователя user={}", user);
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }

        users.remove(user);
        users.put(id, user);
        return ResponseEntity.ok(user);
    }
}
