package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.Film;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<Film.Genre> getGenres() {
        log.info("GET-request to take genre from BD has been received");
        List<Film.Genre> genres = genreService.findAll();

        return genres;
    }

    @GetMapping("/{id}")
    public Film.Genre getGenre(@PathVariable Integer id) {
        log.info("GET-request to take genre by id={} from BD has been received", id);

        Film.Genre genre = genreService.findById(id);
        return genre;
    }
}
