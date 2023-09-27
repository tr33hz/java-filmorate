package ru.yandex.practicum.filmorate.exceptions;

public class NotExictingGenreException extends RuntimeException {
    public NotExictingGenreException(String message) {
        super(message);
    }
}
