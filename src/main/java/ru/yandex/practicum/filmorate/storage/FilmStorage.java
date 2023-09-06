package ru.yandex.practicum.filmorate.storage;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage<Film> {

    List<Film> getAll();

    Film createFilm(Film film);

    Film updateFilm(Film film);

}
