package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getUsers();

    User saveUser(User user);

    Optional<User> findById(Integer id);
}
