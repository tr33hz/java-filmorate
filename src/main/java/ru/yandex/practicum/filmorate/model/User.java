package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
public class User {

    @PositiveOrZero
    private int id;

    @Email(message = "Электронная почта указана неверно")
    private String email;

    @Pattern(regexp = "^\\S+$", message = "Логин не должен содержать пробелов")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
}
