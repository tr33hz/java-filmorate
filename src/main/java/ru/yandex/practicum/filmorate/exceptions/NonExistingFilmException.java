package ru.yandex.practicum.filmorate.exceptions;

public class NonExistingFilmException extends RuntimeException {
    public NonExistingFilmException(String message) {
        super(message);
    }
}
