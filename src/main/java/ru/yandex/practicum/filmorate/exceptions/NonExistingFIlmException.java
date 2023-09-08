package ru.yandex.practicum.filmorate.exceptions;

public class NonExistingFIlmException extends RuntimeException{
    public NonExistingFIlmException(String message) {
        super(message);
    }
}
