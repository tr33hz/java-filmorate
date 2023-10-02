package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void result200IfBirthdayIsToday() throws Exception {
        final LocalDate today = LocalDate.now();
        final String todayString = today.toString();

        mockMvc.perform(post("/users")
                .content(String.format(
                        "{\"login\":\"gang\",\"name\":\"\",\"email\":\"test@mail.ru\",\"birthday\":\"%s\"}", todayString
                )).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void result200IfUserIsOk() throws Exception {
        mockMvc.perform(post("/users")
                .content(
                        "{\"login\":\"nick\",\"name\":\"nick\",\"email\":\"mail@mail.ru\",\"birthday\":\"1999-03-23\"}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void result400IfBirthdayInFuture() throws Exception {
        mockMvc.perform(post("/users")
                .content(
                        "{\"login\":\"toy\",\"name\":\"\",\"email\":\"test@mail.ru\",\"birthday\":\"2333-03-23\"}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void result400IfLoginContainsSpaceCharacters() throws Exception {
        mockMvc.perform(post("/users")
                .content(
                        "{\"login\":\"vcoy\",\"email\":\"kino@mail.ru\",\"birthday\":\"2333-03-23\"}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void result400IfEmailIsIncorrect() throws Exception {
        mockMvc.perform(post("/users")
                .content(
                        "{\"login\":\"lmao\",\"name\":\"\",\"email\":\"mail.ru\",\"birthday\":\"2000-10-10\"}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}
