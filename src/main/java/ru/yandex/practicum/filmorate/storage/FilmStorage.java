package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage<T> {

List<Film> getAll();

Film createFilm(Film film);

Optional<Film> findById(Integer id);

}
