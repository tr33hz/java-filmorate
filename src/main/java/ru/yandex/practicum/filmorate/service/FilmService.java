package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.exceptions.NonExistingFilmException;
import ru.yandex.practicum.filmorate.repository.interfaces.FilmRepository;
import ru.yandex.practicum.filmorate.repository.interfaces.LikeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final LikeRepository likeRepository;
    private final UserService userService;

    @Autowired
    public FilmService(
            @Qualifier("FilmDao") FilmRepository filmRepository,
            LikeRepository likeRepository,
            UserService userService) {
        this.filmRepository = filmRepository;
        this.userService = userService;
        this.likeRepository = likeRepository;
    }

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
                .orElseThrow(() -> new NonExistingFilmException("This film does not exist"));

        User user = userService.getUserById(userId);

        film.addLike(user);
        likeRepository.deleteLikes(film);
        likeRepository.saveLikes(film);

        return film;
    }

    public Film updateFilm(Film film) {
        final int filmId = film.getId();
        filmRepository.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("Error received film by id"));
        return filmRepository.saveFilm(film);
    }

    public Film removeLike(Integer filmId, Integer userId) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new NonExistingFilmException("This film does not exist"));

        User user = userService.getUserById(userId);

        film.removeLike(user);
        likeRepository.deleteLike(film, user);

        return film;
    }
}
