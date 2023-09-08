package ru.yandex.practicum.filmorate.storage;

import java.util.List;
import java.util.Optional;

public interface FilmStorage<Film> {

    List<Film> getAll();

    Film createFilm(Film film);

    Optional<Film> findById(Integer id);

}
