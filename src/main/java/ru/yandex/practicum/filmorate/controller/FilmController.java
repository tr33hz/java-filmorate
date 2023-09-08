package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        List<Film> list = filmService.getFilms();
        return list;
    }

    @GetMapping("{id}")
    public Film getFilmById(@PathVariable Integer id) {
        Film film = filmService.getFilmById(id);
        return film;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        Film savedFilm = filmService.create(film);
        return savedFilm;
    }

    @PutMapping("{filmId}/likes/{userId}")
    public Film addFriend(@PathVariable Integer filmId,
                          @PathVariable Integer userId) {
        return filmService.addLike(filmId, userId);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @DeleteMapping("{filmId}/likes/{userId}")
    public Film removeFriend(@PathVariable Integer filmId,
                             @PathVariable Integer userId) {
        return filmService.removeLike(filmId, userId);
    }
}
