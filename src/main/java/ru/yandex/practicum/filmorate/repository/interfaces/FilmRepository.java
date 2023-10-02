package ru.yandex.practicum.filmorate.repository.interfaces;

import ru.yandex.practicum.filmorate.dto.Film;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {

    Film saveFilm(Film film);

    List<Film> getAll();

    Optional<Film> findById(Integer id);

    public void delete(Film film);

}
