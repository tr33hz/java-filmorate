package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void result400IfNameIsBlank() throws Exception {
        mockMvc.perform(post("/films")
                .content(
                        "{\"name\":\"\",\"description\":\"des\",\"releaseDate\":\"2000-06-03\",\"duration\":200}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void result400IfDurationIsNegative() throws Exception {
        mockMvc.perform(post("/films")
                .content(
                        "{\"name\":\"Топский Павел\",\"description\":\"Всегда на шаг впереди\",\"releaseDate\":\"2014-10-10\",\"duration\":-1}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void result400IfDurationIsZero() throws Exception {
        mockMvc.perform(post("/films")
                .content(
                        "{\"name\":\"Зубенко\",\"description\":\"ор взакони\",\"releaseDate\":\"1999-11-11\",\"duration\":0}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}
