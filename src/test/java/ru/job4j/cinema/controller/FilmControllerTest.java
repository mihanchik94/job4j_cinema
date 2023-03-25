package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.service.FilmService;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

class FilmControllerTest {
    private FilmService filmService;
    private FilmController filmController;

    @BeforeEach
    public void initServices() {
        filmService = Mockito.mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenRequestFilmsListPageThenGetPageWithFilms() {
        FilmDto film1 = new FilmDto(1, "name1", "desc1", 2007, 1, 120, "Ужвсы");
        FilmDto film2 = new FilmDto(2, "name2", "desc2", 2007, 2,121, "Комедия");
        Collection<FilmDto> expectedFilms = List.of(film1, film2);
        when(filmService.findAll()).thenReturn(expectedFilms);

        ConcurrentModel model = new ConcurrentModel();
        String view = filmController.getAll(model);
        Object actualFilms = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }
}