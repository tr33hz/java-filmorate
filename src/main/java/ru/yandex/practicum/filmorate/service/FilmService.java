package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NonExistingFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new NonExistingFilmException("This film does not exist"));
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAll();
    }

    public List<Film> getFilmsByLikes(int count) {
        return filmStorage.getAll().stream()
                .sorted(Comparator.comparingInt(Film::getQuantityLikes).reversed())
                .limit(count).collect(Collectors.toList());
    }

    public Film create(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film addLike(Integer filmId, Integer userId) {
        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("This user does not exist"));

        User user = userService.getUserById(userId);


        film.addLike(user);

        return film;
    }

    public Film updateFilm(Film film) {
        final int filmId = film.getId();
        filmStorage.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("Попытка обновить несуществующий фильм"));
        return filmStorage.createFilm(film);
    }

    public Film removeLike(Integer filmId, Integer userId) {
        Film film = filmStorage.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("This film does not exist"));

        User user = userService.getUserById(userId);


        film.removeLike(user);

        return film;
    }
}
