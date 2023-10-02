package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.exceptions.NotExictingGenreException;
import ru.yandex.practicum.filmorate.repository.interfaces.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Film.Genre findById(Integer id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new NotExictingGenreException("Error received rating by id"));
    }

    public List<Film.Genre> findAll() {
        return genreRepository.findAll();
    }
}
