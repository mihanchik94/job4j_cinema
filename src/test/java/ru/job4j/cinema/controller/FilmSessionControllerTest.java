package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.service.FilmSessionService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {
    private FilmSessionService filmSessionService;
    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionService = Mockito.mock(FilmSessionService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenRequestFilmSessionsListPageThenGetPageWithFilmSessions() {
        FilmSessionDto filmSession1 = new FilmSessionDto(1, "name1", 1, "hall1", LocalDateTime.now(), LocalDateTime.now().plusHours(3), 300);
        FilmSessionDto filmSession2 = new FilmSessionDto(2, "name2", 2, "hall2", LocalDateTime.now(), LocalDateTime.now().plusHours(2), 400);
        Collection<FilmSessionDto> expectedSessions = List.of(filmSession1, filmSession2);
        when(filmSessionService.findAll()).thenReturn(expectedSessions);

        ConcurrentModel model = new ConcurrentModel();
        String view = filmSessionController.getAll(model);
        Object actualSessions = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("sessions/list");
        assertThat(actualSessions).isEqualTo(expectedSessions);
    }
}