package ru.yandex.practicum.filmorate.repository.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.exceptions.NotSavedArgumentException;
import ru.yandex.practicum.filmorate.repository.interfaces.FriendRepository;
import ru.yandex.practicum.filmorate.repository.interfaces.LikeRepository;
import ru.yandex.practicum.filmorate.repository.interfaces.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Qualifier("UserDao")
@Slf4j
public class UserDao implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FriendRepository friendRepository;
    private final LikeRepository likeRepository;

    public UserDao(JdbcTemplate jdbcTemplate, FriendRepository friendRepository, LikeRepository likeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.friendRepository = friendRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    public User saveUser(User user) {
        if (findById(user.getId()).isPresent()) {
            log.info("User={} already exists, the user data from the request has been updated", user.getId());
            return update(user);
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("public")
                .withTableName("users")
                .usingColumns("email", "login", "name", "birthday")
                .usingGeneratedKeyColumns("id");
        insert.compile();

        int id = (int) insert.executeAndReturnKey(Map.of(
                "email", user.getEmail(),
                "login", user.getLogin(),
                "name", user.getName(),
                "birthday", user.getBirthday()
        ));
        User savedUser = findById(id)
                .orElseThrow(() -> new NotSavedArgumentException("Произошла ошибка при сохранении пользователя"));
        return savedUser;
    }

    private User update(User user) {
        final int userId = user.getId();
        String sqlQuery = "UPDATE users SET email = ?, login = ?, name = ?, " +
                "birthday = ? WHERE id = ?;";
        jdbcTemplate.update(
                sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                userId
        );
        User updatedUser = findById(userId)
                .orElseThrow(() -> new NotSavedArgumentException("Произошла ошибка при обновлении пользователя"));
        return updatedUser;
    }


    @Override
    public Optional<User> findById(Integer id) {
        String sqlQuery = "SELECT * FROM users WHERE id = ?;";

        UserMap mapUser = new UserMap();
        User user;
        try {
            user = jdbcTemplate.queryForObject(
                    sqlQuery,
                    mapUser,
                    id
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    @Override
    public void delete(User user) {
        Integer userId = user.getId();
        String sqlQuery = "DELETE FROM users WHERE id = ?;";
        jdbcTemplate.update(sqlQuery, userId);
        likeRepository.deleteLikes(user);
        friendRepository.deleteFriends(user);
        friendRepository.deleteFriendFromUsers(user);
    }

    @Override
    public List<User> getUsers() {
        String sqlQuery = "SELECT * FROM users;";

        UserMap mapper = new UserMap();
        List<User> users = jdbcTemplate.query(
                sqlQuery,
                mapper
        );
        return users;
    }


    private class UserMap implements RowMapper<User> {

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
}
