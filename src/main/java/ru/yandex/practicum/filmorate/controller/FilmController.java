package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("POST-request to save film in BD has been received with body={}", film);

        Film savedFilm = filmService.create(film);
        log.info("The film={} to save in BD successfully created", film.getId());

        return savedFilm;
    }

    @GetMapping("{id}")
    public Film getFilmById(@PathVariable Integer id) {
        log.info("GET-request to take film from BD has been received with id={}", id);
        Film film = filmService.getFilmById(id);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("GET-request to take all film from BD has been received");
        List<Film> list = filmService.getFilms();
        return list;
    }

    @GetMapping("/popular")
    public List<Film> getTopFilmsByLikes(
            @RequestParam(value = "count", defaultValue = "10", required = false) int count
    ) {
        log.info("GET-request to search popular film in BD has been received");
        List<Film> popularFilms = filmService.getFilmsByLikes(count);

        log.info("GET-request was processed successfully");
        return popularFilms;
    }

    @PutMapping("{filmId}/like/{userId}")
    public Film addLikeByUser(@PathVariable Integer filmId,
                              @PathVariable Integer userId) {
        log.info("PUT-request to add a like to film={} in BD has been received", filmId);
        return filmService.addLike(filmId, userId);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("PUT-request to update film in BD has been received with body={}", film);
        return filmService.updateFilm(film);
    }

    @DeleteMapping("{filmId}/like/{userId}")
    public Film removeLikeByFilm(@PathVariable Integer filmId,
                                 @PathVariable Integer userId) {
        log.info("DELETE-request to remove a like to film={} in BD has been received", filmId);
        return filmService.removeLike(filmId, userId);
    }
}
