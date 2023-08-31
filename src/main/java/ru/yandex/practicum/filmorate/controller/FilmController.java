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
    public List<Film> getFilms() {
        if (films.isEmpty()) {
            return Collections.emptyList();

        }
        return new ArrayList<>(films.values());
    }

    @PostMapping("/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        final int id = ++countId;
        film.setId(id);

        films.put(id, film);
        log.debug("Добавлен фильм. {}: ", film.getName());
        return film;
    }

    @PutMapping("/films")
    public ResponseEntity<Film> updateFilm(@Valid @RequestBody Film film) {
        final int id = film.getId();

        if (!films.containsKey(id)) {
            log.warn("Попытка обновить несуществующий фильм film={}", film);
            return new ResponseEntity<>(film, HttpStatus.NOT_FOUND);
        }

        films.put(id, film);
        log.info("Фильм film={} успешно обновлен", film);
        return ResponseEntity.ok(film);
    }
}
