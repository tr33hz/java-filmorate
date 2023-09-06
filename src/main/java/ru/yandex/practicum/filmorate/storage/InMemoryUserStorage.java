package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private Map<Integer, User> users = new HashMap<>();
    private int countId = 0;

    @Override
    public List<User> getUsers() {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(users.values());
    }

    @Override
    public User createUser(User user) {

        if (user.getName() == null) {
            log.debug("user={} имя изменено на логин", user);
            user.setName(user.getLogin());
        }

        final int id = ++countId;
        user.setId(id);

        users.put(id, user);
        log.debug("Пользователь user={} успешно создан", user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        final int id = user.getId();

        if (!users.containsKey(id)) {
            log.warn("Попытка обновить несуществующего пользователя user={}", user);
            return user;
        }

        users.remove(user);
        users.put(id, user);
        return user;
    }
}
