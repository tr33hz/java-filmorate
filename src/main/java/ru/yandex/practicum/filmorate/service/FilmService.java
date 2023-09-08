package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NonExistingFIlmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage<Film> filmStorage;
    private final UserService userService;

    public List<Film> getFilms() {
        return filmStorage.getAll();
    }

    public Film getFilmById(Integer id) {
        return filmStorage.findById(id)
                .orElseThrow(() -> new NonExistingFIlmException("This film does not exist"));
    }

    public Film create(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NonExistingFIlmException("This user does not exist"));

        User user = userService.getUserById(userId);


        film.addLike(user);

        return film;
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NonExistingFIlmException("This film does not exist"));

        User user = userService.getUserById(userId);


        film.removeLike(user);

        return film;
    }
}
