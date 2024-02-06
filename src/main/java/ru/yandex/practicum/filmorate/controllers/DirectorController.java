package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.service.DirectorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/directors")
@RequiredArgsConstructor
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping
    public List<Director> findAllDirectors() {
        return directorService.findAllDirectors();
    }

    @GetMapping("/{directorId}")
    public Director findDirectorById(@PathVariable Integer directorId) {
        return directorService.findDirectorById(directorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Director createDirector(@RequestBody @Valid Director director) {
        return directorService.createDirector(director);
    }

    @PutMapping
    public Director updateDirector(@RequestBody @Valid Director director) {
        return directorService.updateDirector(director);
    }

    @DeleteMapping("/{directorId}")
    public void deleteDirector(@PathVariable Integer directorId) {
        directorService.deleteDirector(directorId);
    }
}
