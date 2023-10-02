package ru.yandex.practicum.filmorate.repository.interfaces;

import ru.yandex.practicum.filmorate.dto.Film;

import java.util.List;
import java.util.Optional;

public interface RatingRepository {

    Optional<Film.RatingMPA> findById(Integer id);

    List<Film.RatingMPA> findAll();
}
