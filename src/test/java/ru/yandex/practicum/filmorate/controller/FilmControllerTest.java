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
    public void result200IfReleaseDateIs28December1895() throws Exception {
        mockMvc.perform(post("/films")
                .content(
            "{\"name\":\"Древнеславянский зажим\",\"description\":\"Топ 1 прием у древних русов\",\"releaseDate\":\"1895-12-28\",\"duration\":200}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void result400IfDurationIsNegative() throws Exception {
        mockMvc.perform(post("/films")
                .content(
            "{\"name\":\"Топский Павел\",\"description\":\"Всегда на шаг впереди\",\"releaseDate\":\"2014-10-10\",\"duration\":-1}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void result200IfFilmPassedTest() throws Exception {
        mockMvc.perform(post("/films")
                .content(
                        "{\"name\":\"Райн Гослинг\",\"description\":\"Придет к тебе в гости...\",\"releaseDate\":\"2016-06-03\",\"duration\":100}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void result200IfDurationIsPositive() throws Exception {
        mockMvc.perform(post("/films")
                .content(
                        "{\"name\":\"название\",\"description\":\"описание\",\"releaseDate\":\"2007-07-07\",\"duration\":1}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void result400IfDurationIsZero() throws Exception {
        mockMvc.perform(post("/films")
                .content(
            "{\"name\":\"Зубенко\",\"description\":\"ор взакони\",\"releaseDate\":\"1999-11-11\",\"duration\":0}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void result400IfDescriptionContainsMoreThan200Symbols() throws Exception {
        mockMvc.perform(post("/films")
                .content(
                        "{\"name\":\"Идущий к реке\",\"description\":\"Я в своем познании настолько преисполнился, что я как будто бы уже\n" +
                                "\n" +
                                "сто триллионов миллиардов лет проживаю на триллионах и\n" +
                                "\n" +
                                "триллионах таких же планет, как эта Земля, мне этот мир абсолютно\n" +
                                "\n" +
                                "понятен, и я здесь ищу только одного - покоя, умиротворения и\n" +
                                "\n" +
                                "вот этой гармонии, от слияния с бесконечно вечным, от созерцания\n" +
                                "\n" +
                                "великого фрактального подобия и от вот этого замечательного всеединства\n" +
                                "\n" +
                                "существа, бесконечно вечного, куда ни посмотри, хоть вглубь - бесконечно\n" +
                                "\n" +
                                "малое, хоть ввысь - бесконечное большое, понимаешь? А ты мне опять со\n" +
                                "\n" +
                                "своим вот этим, иди суетись дальше, это твоё распределение, это\n" +
                                "\n" +
                                "твой путь и твой горизонт познания и ощущения твоей природы, он\n" +
                                "\n" +
                                "несоизмеримо мелок по сравнению с моим, понимаешь? Я как будто бы уже\n" +
                                "\n" +
                                "давно глубокий старец, бессмертный, ну или там уже почти бессмертный,\n" +
                                "\n" +
                                "который на этой планете от её самого зарождения, ещё когда только Солнце\n" +
                                "\n" +
                                "только-только сформировалось как звезда, и вот это газопылевое облако,\n" +
                                "\n" +
                                "вот, после взрыва, Солнца, когда оно вспыхнуло, как звезда, начало\n" +
                                "\n" +
                                "формировать вот эти коацерваты, планеты, понимаешь, я на этой Земле уже\n" +
                                "\n" +
                                "как будто почти пять миллиардов лет живу и знаю её вдоль и поперёк\n" +
                                "\n" +
                                "этот весь мир, а ты мне какие-то... мне не важно на твои тачки, на твои\n" +
                                "\n" +
                                "яхты, на твои квартиры, там, на твоё благо. Я был на этой\n" +
                                "\n" +
                                "планете бесконечным множеством, и круче Цезаря, и круче Гитлера, и круче\n" +
                                "\n" +
                                "всех великих, понимаешь, был, а где-то был конченым говном, ещё хуже,\n" +
                                "\n" +
                                "чем здесь. Я множество этих состояний чувствую. Где-то я был больше\n" +
                                "\n" +
                                "подобен растению, где-то я больше был подобен птице, там, червю, где-то\n" +
                                "\n" +
                                "был просто сгусток камня, это всё есть душа, понимаешь?\",\"releaseDate\":\"2020-06-20\",\"duration\":200}"
                ).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }
}
