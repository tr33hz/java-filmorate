package ru.yandex.practicum.filmorate.repository.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.repository.interfaces.UserRepository;

import java.util.*;

@Slf4j
@Repository
@Qualifier("InMemoryUserStorage")
public class InMemoryUserStorage implements UserRepository {

    private final Map<Integer, User> users;
    private TaskIdUserGenerator taskIdUserGenerator;

    @Autowired
    public InMemoryUserStorage() {
        this.users = new HashMap<>();
        this.taskIdUserGenerator = new TaskIdUserGenerator();
    }

    @Override
    public List<User> getUsers() {
        if (users.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }


    @Override
    public User saveUser(User user) {
        final Integer userId = user.getId();

        if (userId != 0 && userId <= taskIdUserGenerator.nextFreeId && users.containsKey(userId)) {
            users.put(userId, user);
            return user;
        }

        final int id = taskIdUserGenerator.getNextFreeId();
        user.setId(id);
        users.put(id, user);

        return user;
    }

    protected class TaskIdUserGenerator {
        private int nextFreeId = 1;

        public int getNextFreeId() {
            return nextFreeId++;
        }
    }
}
