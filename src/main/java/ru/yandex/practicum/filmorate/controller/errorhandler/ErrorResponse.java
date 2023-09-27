package ru.yandex.practicum.filmorate.controller.errorhandler;

import lombok.Value;

@Value
public class ErrorResponse {

    String message;
    Class<? extends Throwable> cause;
}
