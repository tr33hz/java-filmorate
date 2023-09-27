package ru.yandex.practicum.filmorate.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.repository.interfaces.FilmRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class FilmRepositoryTests {

    private final FilmRepository filmRepository;
    private Film savedFilm;
    private static final AtomicInteger expectedId = new AtomicInteger(0);

    @Autowired
    public FilmRepositoryTests(
            @Qualifier("FilmDao") FilmRepository filmRepository
    ) {
        this.filmRepository = filmRepository;
    }

    @BeforeEach
    public void saveFilm() {
        savedFilm = filmRepository.saveFilm(
                Film.builder()
                        .name("test")
                        .description("test")
                        .releaseDate(LocalDate.now())
                        .duration(120)
                        .mpa(new Film.RatingMPA(1, "G"))
                        .id(1)
                        .build()

        );
    }

    @AfterEach
    public void deleteAllFilms() {
        filmRepository.getAll().forEach(filmRepository::delete);
    }

    @Test
    public void createAndReturnFilmById() {
        assertNotNull(savedFilm);

        Integer id = expectedId.incrementAndGet();
        Integer filmId = savedFilm.getId();
        assertEquals(id, filmId);
    }

    @Test
    public void createAndGetById() {
        Integer id = expectedId.incrementAndGet();
        Optional<Film> optionalFilm = filmRepository.findById(id);
        assertTrue(optionalFilm.isPresent());

        Film foundFilm = optionalFilm.get();
        assertEquals(savedFilm, foundFilm);
    }

    @Test
    public void deleteFilmAfterSave() {
        filmRepository.delete(savedFilm);

        Integer id = expectedId.incrementAndGet();
        Optional<Film> optionalFilm = filmRepository.findById(id);

        assertTrue(optionalFilm.isEmpty());
    }
}
