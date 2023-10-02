package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public List<Film.RatingMPA> getRatings() {
        log.info("GET-request to take rating from BD has been received");
        List<Film.RatingMPA> ratings = ratingService.findAll();

        return ratings;
    }

    @GetMapping("/{id}")
    public Film.RatingMPA getRating(@PathVariable Integer id) {
        log.info("GET-request to take rating by id={} from BD has been received", id);
        Film.RatingMPA rating = ratingService.findById(id);

        return rating;
    }
}
