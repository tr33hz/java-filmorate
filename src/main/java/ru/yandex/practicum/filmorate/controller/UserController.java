package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("{id}/friends")
    public Set<User> getListFriendsUsers(@PathVariable Integer id) {
        Set<User> listFriends = userService.getAllFriendsUser(id);
        return listFriends;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return user;
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Set<Integer> getCommonFriends(@PathVariable Integer id,
                                         @PathVariable Integer otherId) {
        Set<Integer> commonFriends = userService.getAllCommonFriends(id, otherId);
        return commonFriends;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        User userSaved = userService.create(user);
        return userSaved;
    }

    @PutMapping("{id}/friends/{friendId}")
    public User addFriends(@PathVariable Integer id,
                           @PathVariable Integer friendId) {
        return userService.addFriend(id, friendId);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        User userUpdate = userService.updateUser(user);
        return userUpdate;
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public User removeFriend(@PathVariable Integer id,
                             @PathVariable Integer friendId) {
        return userService.removeUser(id, friendId);
    }
}
