package ru.yandex.practicum.filmorate.repository;

import ru.yandex.practicum.filmorate.dto.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {

    List<Film> getAll();

    Film saveFilm(Film film);

    Optional<Film> findById(Integer id);

}
