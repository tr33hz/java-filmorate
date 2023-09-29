package ru.yandex.practicum.filmorate.repository.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.exceptions.NotSavedArgumentException;
import ru.yandex.practicum.filmorate.repository.interfaces.GenreRepository;
import ru.yandex.practicum.filmorate.repository.interfaces.LikeRepository;
import ru.yandex.practicum.filmorate.repository.interfaces.RatingRepository;
import ru.yandex.practicum.filmorate.repository.interfaces.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class FilmMapper implements RowMapper<Film> {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;
    private final LikeRepository likeRepository;

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        int ratingId = rs.getInt("rating_mpa_id");
        Film.RatingMPA rating = ratingRepository.findById(ratingId)
                .orElseThrow(() -> new NotSavedArgumentException("Error by received rating film"));

        int filmId = rs.getInt("id");
        Set<Film.Genre> genres = new HashSet<>(genreRepository.findGenresByFilmId(filmId));
        List<Integer> likes = likeRepository.findLikesByFilmId(filmId);

        Film film = Film.builder()
                .mpa(rating)
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .duration(rs.getInt("duration"))
                .id(filmId)
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .genres(genres)
                .build();
        likes.stream().map(userRepository::findById)
                .map(opt -> opt.orElseThrow(
                        () -> new NotSavedArgumentException("Error like user")
                )).forEach(film::addLike);

        return film;
    }
}
