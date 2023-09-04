package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private int countId = 0;

    @GetMapping("/films")
    public ResponseEntity<List<Film>> getFilms() {
        if (films.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());

        }
        return ResponseEntity.ok(new ArrayList<>(films.values()));
    }

    @PostMapping("/films")
    public ResponseEntity<Film> createFilm(@Valid @RequestBody Film film) {
        final int id = ++countId;
        film.setId(id);

        films.put(id, film);
        log.debug("Добавлен фильм. {}: ", film.getName());
        return ResponseEntity.ok(film);
    }

    @PutMapping("/films")
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        final int id = film.getId();

        if (!films.containsKey(id)) {
            log.warn("Попытка обновить несуществующий фильм film={}", film);
            return new ResponseEntity<>(film, HttpStatus.NOT_FOUND);
        }

        films.remove(film);
        films.put(id, film);
        return ResponseEntity.ok(film);
    }
}
