package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {

    private int id;

    @Email(message = "Электронная почта указана неверно")
    private String email;

    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелов")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Integer> friends = new HashSet<>();

    public void addFriend(User user) {
        final Integer id = user.getId();

        if (friends.add(id)) {
            user.addFriend(this);
        }
    }

    public void removeFriend(User user) {
        final Integer id = user.getId();

        if (friends.remove(id)) {
            user.removeFriend(this);
        }
    }

    public Integer getQuantityFriends() {
        return friends.size();
    }
}
