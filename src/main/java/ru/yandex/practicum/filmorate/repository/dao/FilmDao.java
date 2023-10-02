package ru.yandex.practicum.filmorate.repository.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.exceptions.NotSavedArgumentException;
import ru.yandex.practicum.filmorate.repository.interfaces.*;
import ru.yandex.practicum.filmorate.repository.mappers.FilmMapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Qualifier
public class FilmDao implements FilmRepository {

    private final JdbcTemplate jdbcTemplate;
    @Qualifier("userDao")
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;
    private final LikeRepository likeRepository;

    @Override
    public Film saveFilm(Film film) {
        if (findById(film.getId()).isPresent()) {
            return update(film);
        }
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withSchemaName("public")
                .withTableName("film")
                .usingColumns("name", "description", "release_date", "duration", "rating_mpa_id")
                .usingGeneratedKeyColumns("id");
        insert.compile();

        int id = (int) insert.executeAndReturnKey(Map.of(
                "name", film.getName(),
                "description", film.getDescription(),
                "release_date", film.getReleaseDate(),
                "duration", film.getDuration(),
                "rating_mpa_id", film.getMpa().getId()
        ));
        film.setId(id);
        genreRepository.saveGenres(film);

        Film savedFilm = findById(id)
                .orElseThrow(() -> new NotSavedArgumentException("Error by created film"));
        return savedFilm;
    }

    private Film update(Film film) {
        final int filmId = film.getId();
        String sqlQuery = "UPDATE film SET name = ?, description = ?, release_date = ?, " +
                "duration = ?, rating_mpa_id = ? WHERE id = ?;";
        if (!genreRepository.findGenresByFilmId(filmId).equals(film.getGenres())) {
            genreRepository.deleteGenres(film);
            genreRepository.saveGenres(film);
        }
        jdbcTemplate.update(
                sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                filmId
        );
        Film updatedFilm = findById(filmId)
                .orElseThrow(() -> new NotSavedArgumentException("Error by update film"));
        return updatedFilm;
    }

    @Override
    public Optional<Film> findById(Integer id) {
        String sqlQuery = "SELECT * FROM film WHERE id = ?;";

        FilmMapper mapper = new FilmMapper(userRepository, genreRepository,
                ratingRepository, likeRepository);
        Film film;
        try {
            film = jdbcTemplate.queryForObject(
                    sqlQuery,
                    mapper,
                    id
            );
        } catch (DataAccessException e) {
            return Optional.empty();
        }
        if (film == null) {
            return Optional.empty();
        }

        return Optional.of(film);
    }

    @Override
    public List<Film> getAll() {
        String sqlQuery = "SELECT * FROM film;";

        FilmMapper filmMapper = new FilmMapper(userRepository, genreRepository,
                ratingRepository, likeRepository);
        List<Film> films = jdbcTemplate.query(
                sqlQuery,
                filmMapper
        );
        return films;
    }

    @Override
    public void delete(Film film) {
        final int filmId = film.getId();
        String sqlQuery = "DELETE FROM film WHERE id = ?;";
        jdbcTemplate.update(sqlQuery, filmId);
        genreRepository.deleteGenres(film);
        likeRepository.deleteLikes(film);
    }
}
