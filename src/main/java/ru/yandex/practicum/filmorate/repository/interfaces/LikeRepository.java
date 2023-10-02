package ru.yandex.practicum.filmorate.repository.interfaces;


import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.dto.User;

import java.util.List;

public interface LikeRepository {

    void saveLikes(Film film);

    List<Integer> findLikesByFilmId(int filmId);

    void deleteLike(Film film, User user);

    void deleteLikes(User user);

    void deleteLikes(Film film);
}
