package ru.yandex.practicum.filmorate.annotaions;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class InvalidFilmCreationDateValidator implements ConstraintValidator<InvalidFilmCreationAnnotaion, LocalDate> {

    @Override
    public void initialize(InvalidFilmCreationAnnotaion constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isAfter(LocalDate.of(1895, 12, 27));
    }
}
