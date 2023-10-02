package ru.yandex.practicum.filmorate.repository.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.repository.interfaces.FriendRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class UserMapper implements RowMapper<User> {

    private final FriendRepository friendRepository;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        final int userId = rs.getInt("id");
        User user = User.builder()
                .id(userId)
                .email(rs.getString("email"))
                .login(rs.getString("login"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .name(rs.getString("name"))
                .build();
        List<Integer> friends = friendRepository.findFriendsByUserId(userId);
        friends.forEach(user::addFriend);
        return user;
    }
}
