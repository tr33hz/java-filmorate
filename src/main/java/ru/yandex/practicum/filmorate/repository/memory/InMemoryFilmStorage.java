package ru.yandex.practicum.filmorate.repository.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.repository.interfaces.FilmRepository;

import java.util.*;

@Slf4j
@Repository
@Qualifier("InMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmRepository {

    private final Map<Integer, Film> films;
    private final TaskIdFilmGenerator taskIdFilmGenerator;

    @Autowired
    public InMemoryFilmStorage() {
        this.films = new HashMap<>();
        this.taskIdFilmGenerator = new TaskIdFilmGenerator();
    }

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
    public void delete(Film film) {
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
