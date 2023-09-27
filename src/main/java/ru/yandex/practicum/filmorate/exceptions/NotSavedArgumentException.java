package ru.yandex.practicum.filmorate.exceptions;

public class NotSavedArgumentException extends RuntimeException {
    public NotSavedArgumentException(String message) {
        super(message);
    }
}
