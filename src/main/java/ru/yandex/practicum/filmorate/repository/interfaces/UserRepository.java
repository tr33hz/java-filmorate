package ru.yandex.practicum.filmorate.repository.interfaces;

import ru.yandex.practicum.filmorate.dto.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User saveUser(User user);

    List<User> getUsers();

    Optional<User> findById(Integer id);

    public void delete(User user);
}
