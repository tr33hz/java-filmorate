package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

public interface UserStorage<User> {
    List<User> getUsers();
    User createUser(User user);
    Optional<User> findById(Integer id);
}
