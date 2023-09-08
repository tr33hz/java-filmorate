package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NonExistingUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage<User> userStorage;

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById (Integer id) {
        return userStorage.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));
    }

    public Set<Integer> getAllFriendsUser (Integer id) {
        User mainUser = userStorage.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        Set<Integer> listFriends = mainUser.getFriends();
        return listFriends;
    }

    public User create(User user) {
        return userStorage.createUser(user);
    }

    public User addFriend(Integer id, Integer friendId) {
        User firstUser = userStorage.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        User secondUser = userStorage.findById(friendId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        firstUser.addFriend(secondUser);

        return firstUser;
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public User removeUser(Integer id, Integer friendId) {
        User firstUser = userStorage.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        User secondUser = userStorage.findById(friendId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        firstUser.removeFriend(secondUser);

        return firstUser;
    }
}
