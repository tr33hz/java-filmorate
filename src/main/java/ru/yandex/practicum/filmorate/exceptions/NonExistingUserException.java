package ru.yandex.practicum.filmorate.exceptions;

public class NonExistingUserException extends RuntimeException {
    public NonExistingUserException(String message) {
        super(message);
    }
}
