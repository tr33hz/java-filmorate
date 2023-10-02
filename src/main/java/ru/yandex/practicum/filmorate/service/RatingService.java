package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.exceptions.NotExictingRatingException;
import ru.yandex.practicum.filmorate.repository.interfaces.RatingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    public Film.RatingMPA findById(Integer id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new NotExictingRatingException("Error received rating by id"));
    }

    public List<Film.RatingMPA> findAll() {
        return ratingRepository.findAll();
    }
}
