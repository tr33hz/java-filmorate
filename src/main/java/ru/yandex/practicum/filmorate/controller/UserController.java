package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        log.info("A request to get all a user has been received");
        return userService.getUsers();
    }

    @GetMapping("{id}/friends")
    public List<User> getListFriendsUsers(@PathVariable Integer id) {
        List<User> listFriends = userService.getAllFriendsUser(id);
        return listFriends;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id) {
        log.info("A request to get a user has been received with id={}", id);
        User user = userService.getUserById(id);
        return user;
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer id,
                                       @PathVariable Integer otherId) {
        List<User> commonFriends = userService.getAllCommonFriends(id, otherId);
        return commonFriends;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.info("A request to create a user has been received with body={}", user);

        User userSaved = userService.create(user);
        log.info("The user={} has been successfully created", userSaved);

        return userSaved;
    }

    @PutMapping("{id}/friends/{friendId}")
    public User addFriends(@PathVariable Integer id,
                           @PathVariable Integer friendId) {
        return userService.addFriend(id, friendId);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("A request to update a user has been received with body={}", user);

        User userUpdate = userService.updateUser(user);
        log.info("The user={} has been successfully updated", userUpdate);
        return userUpdate;
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public User removeFriend(@PathVariable Integer id,
                             @PathVariable Integer friendId) {
        return userService.removeUser(id, friendId);
    }
}
