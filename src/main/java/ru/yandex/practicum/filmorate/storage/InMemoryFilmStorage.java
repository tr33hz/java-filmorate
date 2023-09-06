package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Slf4j
@Repository
public class InMemoryFilmStorage implements FilmStorage<Film> {

    private Map<Integer, Film> films = new HashMap<>();
    private int countId = 0;

    @Override
    public List<Film> getAll() {
        if (films.isEmpty()) {
            return Collections.emptyList();

        }
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        final int id = ++countId;
        film.setId(id);

        films.put(id, film);
        log.debug("Добавлен фильм. {}: ", film.getName());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        final int id = film.getId();

        if (!films.containsKey(id)) {
            log.warn("Попытка обновить несуществующий фильм film={}", film);
            return film;
        }

        films.remove(film);
        films.put(id, film);
        return film;
    }
}
