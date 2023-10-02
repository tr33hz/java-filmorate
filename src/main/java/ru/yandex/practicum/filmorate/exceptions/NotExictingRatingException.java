package ru.yandex.practicum.filmorate.exceptions;

public class NotExictingRatingException extends RuntimeException {
    public NotExictingRatingException(String message) {
        super(message);
    }
}
