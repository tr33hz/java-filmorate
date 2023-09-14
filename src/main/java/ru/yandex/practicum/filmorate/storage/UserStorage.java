package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage<T> {
    List<User> getUsers();

    User createUser(User user);

    Optional<User> findById(Integer id);
}
