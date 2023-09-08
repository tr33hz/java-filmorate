package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.annotaions.InvalidFilmCreationAnnotaion;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {

    @PositiveOrZero
    private int id;

    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @Length(max = 200, message = "Описание фильма не должно быть более 200 символов")
    private String description;

    @InvalidFilmCreationAnnotaion(message = "Не соответствующая дата релиза")
    private LocalDate releaseDate;

    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    @EqualsAndHashCode.Exclude
    private Set<Integer> likes = new HashSet<>();


    public void addLike(User user) {
        final Integer id = user.getId();

        likes.add(id);
    }

    public void removeLike(User user) {
        final Integer id = user.getId();

        likes.remove(id);
    }

    public Integer getQuantityLikes() {
        return likes.size();
    }
}
