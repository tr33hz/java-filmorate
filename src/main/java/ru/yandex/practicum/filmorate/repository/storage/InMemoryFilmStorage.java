package ru.yandex.practicum.filmorate.repository.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.dto.Film;

import java.util.*;

@Slf4j
@Repository
public class InMemoryFilmStorage implements FilmRepository {

    private Map<Integer, Film> films = new HashMap<>();
    private TaskIdFilmGenerator taskIdFilmGenerator = new TaskIdFilmGenerator();

    @Override
    public List<Film> getAll() {
        if (films.isEmpty()) {
            return Collections.emptyList();

        }
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> findById(Integer id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film saveFilm(Film film) {
        final int filmId = film.getId();
        if (filmId != 0 && filmId <= taskIdFilmGenerator.nextFreeId && films.containsKey(filmId)) {
            films.put(filmId, film);
            return film;
        }

        final int newId = taskIdFilmGenerator.getNextFreeId();
        film.setId(newId);
        films.put(newId, film);

        return film;
    }


    protected class TaskIdFilmGenerator {
        private int nextFreeId = 1;

        public int getNextFreeId() {
            return nextFreeId++;
        }
    }
}
