package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NonExistingFilmException;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.repository.FilmRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final UserService userService;

    public List<Film> getFilms() {
        return filmRepository.getAll();
    }

    public Film getFilmById(Integer id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new NonExistingFilmException("This film does not exist"));
    }

    public List<Film> getAllFilms() {
        return filmRepository.getAll();
    }

    public List<Film> getFilmsByLikes(int count) {
        return filmRepository.getAll().stream()
                .sorted(Comparator.comparingInt(Film::getQuantityLikes).reversed())
                .limit(count).collect(Collectors.toList());
    }

    public Film create(Film film) {
        return filmRepository.saveFilm(film);
    }

    public Film addLike(Integer filmId, Integer userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("This user does not exist"));

        User user = userService.getUserById(userId);

        film.addLike(user);

        return film;
    }

    public Film updateFilm(Film film) {
        final int filmId = film.getId();
        filmRepository.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("Попытка обновить несуществующий фильм"));
        return filmRepository.saveFilm(film);
    }

    public Film removeLike(Integer filmId, Integer userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("This film does not exist"));

        User user = userService.getUserById(userId);

        film.removeLike(user);

        return film;
    }
}
