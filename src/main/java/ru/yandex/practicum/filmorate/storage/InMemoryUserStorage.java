package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@Repository
public class InMemoryUserStorage implements UserStorage<User> {

private Map<Integer, User> users = new HashMap<>();
private TaskIdUserGenerator taskIdUserGenerator = new TaskIdUserGenerator();

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
    public User createUser(User user) {
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
