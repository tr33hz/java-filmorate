package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private InMemoryFilmStorage inMemoryFilmStorage;

    public FilmController() {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    @GetMapping("/films")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    @PostMapping("/films")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Film createFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.createFilm(film);
    }

    @PutMapping("/films")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Film updateFilm(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.updateFilm(film);
    }
}
