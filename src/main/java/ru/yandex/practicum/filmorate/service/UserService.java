package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NonExistingUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserStorage<User> userStorage;

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(Integer id) {
        return userStorage.findById(id) // должен возвращать 404
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));
    }

    public Set<User> getAllFriendsUser(Integer id) {
        User mainUser = userStorage.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        Set<User> listFriends = mainUser.getFriends()
                .stream()
                .map(userStorage::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toUnmodifiableSet());
        return listFriends;
    }


    public Set<Integer> getAllCommonFriends(Integer id, Integer otherId) {
        User firstUser = userStorage.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        User secondUser = userStorage.findById(otherId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        Set<Integer> commonFriends = firstUser.getFriends()
                .stream()
                .filter(secondUser.getFriends()::contains)
                .collect(Collectors.toUnmodifiableSet());

        return commonFriends;
    }


    public User create(User user) {
        final String userName = user.getName();
        if (user.getName() == null || userName.isBlank()) {
            log.debug("user={} имя изменено на логин", user);
            user.setName(user.getLogin());
        }

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
        final Integer userId = user.getId();

        User firstUser = userStorage.findById(userId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        return create(user);
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
