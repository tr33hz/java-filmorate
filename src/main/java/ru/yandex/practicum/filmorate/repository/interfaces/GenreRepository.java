package ru.yandex.practicum.filmorate.repository.interfaces;


import ru.yandex.practicum.filmorate.dto.Film;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    void saveGenres(Film film);

    List<Film.Genre> findGenresByFilmId(int filmId);

    Optional<Film.Genre> findById(Integer id);

    List<Film.Genre> findAll();

    void deleteGenres(Film film);
}
