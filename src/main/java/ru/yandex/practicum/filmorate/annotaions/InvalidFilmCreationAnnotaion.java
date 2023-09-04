package ru.yandex.practicum.filmorate.annotaions;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = InvalidFilmCreationDateValidator.class)
@Documented
public @interface InvalidFilmCreationAnnotaion {
    String message() default "{ru.yandex.practicum.filmorate.util.validation.InvalidFilmCreationAnnotaion.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
