package ru.yandex.practicum.filmorate.repository.interfaces;


import ru.yandex.practicum.filmorate.dto.User;

import java.util.List;

public interface FriendRepository {

    void saveFriends(User user);

    List<Integer> findFriendsByUserId(int userId);

    void deleteFriend(User user, User friend);

    void deleteFriends(User user);

    void deleteFriendFromUsers(User friend);
}
