package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.exceptions.NonExistingUserException;
import ru.yandex.practicum.filmorate.repository.interfaces.FriendRepository;
import ru.yandex.practicum.filmorate.repository.interfaces.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Component
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Autowired
    public UserService(
            @Qualifier("UserDao") UserRepository userRepository,
            FriendRepository friendRepository
    ) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id) // должен возвращать 404
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));
    }

    public List<User> getAllFriendsUser(Integer id) {
        User mainUser = userRepository.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        return mainUser.getFriends()
                .stream()
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }


    public List<User> getAllCommonFriends(Integer id, Integer otherId) {
        User firstUser = userRepository.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        User secondUser = userRepository.findById(otherId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        List<User> friends1 = getAllFriendsUser(firstUser.getId());
        List<User> friends2 = getAllFriendsUser(secondUser.getId());

        friends1.removeIf(friend -> !friends2.contains(friend));

        return friends1;
    }


    public User create(User user) {
        final String userName = user.getName();
        if (user.getName() == null || userName.isBlank()) {
            log.debug("user={} name changed to login", user);
            user.setName(user.getLogin());
        }

        return userRepository.saveUser(user);
    }

    public User addFriend(Integer id, Integer friendId) {
        User firstUser = userRepository.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        User secondUser = userRepository.findById(friendId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        firstUser.addFriend(secondUser);
        friendRepository.deleteFriends(firstUser);
        friendRepository.saveFriends(firstUser);

        return firstUser;
    }

    public User updateUser(User user) {
        final Integer userId = user.getId();

        userRepository.findById(userId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        return create(user);
    }

    public User removeUser(Integer id, Integer friendId) {
        User firstUser = userRepository.findById(id)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        User secondUser = userRepository.findById(friendId)
                .orElseThrow(() -> new NonExistingUserException("This user does not exist"));

        firstUser.removeFriend(secondUser);
        friendRepository.deleteFriend(firstUser, secondUser);

        return firstUser;
    }
}
