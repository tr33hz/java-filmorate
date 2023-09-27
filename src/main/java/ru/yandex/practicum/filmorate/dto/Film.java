package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.exceptions.NotSavedArgumentException;
import ru.yandex.practicum.filmorate.util.annotaions.InvalidFilmCreationAnnotaion;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
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
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private final Set<Integer> likes = new HashSet<>();

    private Set<Genre> genres;

    @NotNull
    private RatingMPA mpa;


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

    @Data
    public static class Genre {

        private int id;
        @EqualsAndHashCode.Exclude
        private String name;

        public Genre(int id, String name) {
            setId(id);
            this.name = name;
        }

        public void setId(int id) {
            if (id < 1 || id > 6) {
                throw new NotSavedArgumentException("Uncorrected received id");
            }
            this.id = id;
        }
    }

    @Data
    public static class RatingMPA {

        private int id;
        @EqualsAndHashCode.Exclude
        private String name;

        public RatingMPA(int id, String name) {
            setId(id);
            this.name = name;
        }

        public void setId(int id) {
            if (id < 1 || id > 5) {
                throw new NotSavedArgumentException("Uncorrected received id");
            }
            this.id = id;
        }
    }
}

