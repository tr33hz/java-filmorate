package ru.yandex.practicum.filmorate.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dto.User;
import ru.yandex.practicum.filmorate.repository.interfaces.UserRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class UserRepositoryTests {

    private final UserRepository userRepository;
    private User savedUser;
    private static final AtomicInteger expectedId = new AtomicInteger(0);

    @Autowired
    public UserRepositoryTests(
            @Qualifier("UserDao") UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void saveUser() {
        savedUser = userRepository.saveUser(
                User.builder()
                        .name("trhz")
                        .login("trhz")
                        .email("trhz@yandex.ru")
                        .birthday(LocalDate.now())
                        .id(1)
                        .build()
        );
    }

    @AfterEach
    public void afterEach() {
        userRepository.getUsers().forEach(userRepository::delete);
    }

    @Test
    public void createdAndReturnUserById() {
        assertNotNull(savedUser);

        Integer id = expectedId.incrementAndGet();
        Integer userId = savedUser.getId();
        assertEquals(id, userId);
    }

    @Test
    public void findByIdAfterSave() {
        Integer id = expectedId.incrementAndGet();
        Optional<User> optionalUser = userRepository.findById(id);
        assertTrue(optionalUser.isPresent());

        User foundUser = optionalUser.get();
        assertEquals(savedUser, foundUser);
    }

    @Test
    public void deleteUserAfterSave() {
        userRepository.delete(savedUser);

        Integer id = expectedId.incrementAndGet();
        Optional<User> optionalUser = userRepository.findById(id);

        assertTrue(optionalUser.isEmpty());
    }
}
