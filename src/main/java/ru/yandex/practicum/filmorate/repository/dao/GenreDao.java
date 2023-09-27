package ru.yandex.practicum.filmorate.repository.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.repository.interfaces.GenreRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
//dsa
@Repository
@RequiredArgsConstructor
public class GenreDao implements GenreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveGenres(Film film) {
        final int filmId = film.getId();
        List<Integer> genreIds = getGenreIds(film.getGenres());
        for (int genreId : genreIds) {
            saveGenreOfFilm(filmId, genreId);
        }
    }

    private void saveGenreOfFilm(int filmId, int genreId) {
        String sqlQuery = "INSERT INTO genre_film (film_id, genre_id) VALUES (?, ?);";
        jdbcTemplate.update(
                sqlQuery,
                filmId,
                genreId
        );
    }

    private List<Integer> getGenreIds(Set<Film.Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return Collections.emptyList();
        }
        return genres.stream()
                .map(Film.Genre::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Film.Genre> findGenresByFilmId(int filmId) {
        String sqlQuery = "SELECT gf.genre_id," +
                "g.name " +
                "FROM genre_film AS gf " +
                "JOIN genre AS g ON gf.genre_id = g.id " +
                "WHERE gf.film_id = ?;";
        List<Film.Genre> genres = jdbcTemplate.query(
                sqlQuery,
                (rs, rowNum) -> new Film.Genre(
                        rs.getInt("genre_id"), rs.getString("name")
                ),
                filmId
        );
        return genres;
    }

    @Override
    public Optional<Film.Genre> findById(Integer id) {
        String sqlQuery = "SELECT * FROM genre WHERE id = ?;";
        Film.Genre genre = jdbcTemplate.queryForObject(
                sqlQuery,
                (rs, rowNum) -> new Film.Genre(
                        rs.getInt("id"), rs.getString("name")
                ),
                id
        );
        if (genre == null) {
            return Optional.empty();
        }
        return Optional.of(genre);
    }

    @Override
    public List<Film.Genre> findAll() {
        String sqlQuery = "SELECT * FROM genre;";
        List<Film.Genre> genres = jdbcTemplate.query(
                sqlQuery,
                (rs, rowNum) -> new Film.Genre(
                        rs.getInt("id"), rs.getString("name")
                )
        );
        return genres;
    }

    @Override
    public void deleteGenres(Film film) {
        final int filmId = film.getId();
        String sqlQuery = "DELETE FROM genre_film WHERE film_id = ?;";
        jdbcTemplate.update(sqlQuery, filmId);
    }
}
