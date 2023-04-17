package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.*;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class TicketControllerTest {
    private TicketService ticketService;
    private FilmSessionService filmSessionService;
    private HallService hallService;
    private TicketController ticketController;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void initServices() {
        ticketService = Mockito.mock(TicketService.class);
        filmSessionService = Mockito.mock(FilmSessionService.class);
        hallService = Mockito.mock(HallService.class);
        ticketController = new TicketController(ticketService, filmSessionService, hallService);
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }


    @Test
    public void whenGetBuyTicketFormThenRedirectToBuyTicketPage() {
        FilmSessionDto filmSession = new FilmSessionDto();
        Hall hall = new Hall();
        Ticket ticket = new Ticket();
        when(filmSessionService.findById(anyInt())).thenReturn(Optional.of(filmSession));
        when(hallService.findById(anyInt())).thenReturn(Optional.of(hall));

        ConcurrentModel model = new ConcurrentModel();
        String view = ticketController.buyTicketPage(1, model);
        Object actualFilmSession = model.getAttribute("filmSession");
        Object actualRows = model.getAttribute("rows");
        Object actualPlaces = model.getAttribute("places");
        Object actualTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/buyTicket");
        assertThat(actualFilmSession).isEqualTo(filmSession);
        assertThat(actualRows).isEqualTo(new ArrayList<>());
        assertThat(actualPlaces).isEqualTo(new ArrayList<>());
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenPostBuyTicketPageSuccessfulThenRedirectToPurchasesPage() {
        Ticket ticket = new Ticket();
        TicketDto ticketDto = new TicketDto();
        User user = new User();
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticketDto));
        when(session.getAttribute("user")).thenReturn(user);

        ConcurrentModel model = new ConcurrentModel();
        String view = ticketController.buyTicket(ticket, model, request);

        assertThat(view).isEqualTo("redirect:/tickets/purchases/" + ticket.getId());
    }

    @Test
    public void whenPostBuyTicketAndTheSameTicketThenRedirectToErrorPage() {
        Ticket first = new Ticket(1, 1, 1, 1, 1);
        Ticket second = new Ticket(1, 1, 1, 1, 1);
        TicketDto ticketDto = new TicketDto();
        User user = new User();
        when(ticketService.save(first)).thenReturn(Optional.of(ticketDto));
        when(session.getAttribute("user")).thenReturn(user);

        ConcurrentModel model = new ConcurrentModel();
        String view = ticketController.buyTicket(second, model, request);

        assertThat(view).isEqualTo("errors/404");
    }

    @Test
    public void whenGetPurchasesPageSuccessfulThenGetMessageAboutPurchase() {
        Ticket ticket = new Ticket();
        TicketDto ticketDto = new TicketDto();
        User user = new User();
        when(ticketService.findById(anyInt())).thenReturn(Optional.of(ticketDto));
        when(session.getAttribute("user")).thenReturn(user);

        ConcurrentModel model = new ConcurrentModel();
        String view = ticketController.boughtTicketPage(ticket.getId(), model, request);
        Object actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("tickets/purchases");
        assertThat(actualMessage).isEqualTo("Вы успешно приобрели билет на фильм " + ticketDto.getFilmName()
                + " в " + ticketDto.getHallName() + ". Ваш ряд " + ticketDto.getRowNumber() + ", ваше место "
                + ticketDto.getPlaceNumber() + " начало сеанса: " + ticketDto.getStartTime());
    }

    @Test
    public void whenGetPurchasesPageFailedThenRedirectToErrorPage() {
        Ticket ticket = new Ticket();
        User user = new User();
        when(ticketService.findById(anyInt())).thenReturn(Optional.empty());
        when(session.getAttribute("user")).thenReturn(user);

        ConcurrentModel model = new ConcurrentModel();
        String view = ticketController.boughtTicketPage(ticket.getId(), model, request);
        Object actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo("Билет не найден!");
    }

    @Test
    public void whenGetPurchasesPageAndTheDifferentUsersThenRedirectToErrorPage() {
        User user1 = new User(1, "test", "test", "test");
        User user2 = new User(2, "test", "test", "test");
        Ticket ticket = new Ticket(1, 1, 1, 1, user1.getId());
        TicketDto ticketDto = new TicketDto();
        when(ticketService.findById(anyInt())).thenReturn(Optional.of(ticketDto));
        when(session.getAttribute("user")).thenReturn(user2);

        ConcurrentModel model = new ConcurrentModel();
        String view = ticketController.boughtTicketPage(ticket.getId(), model, request);
        Object actualMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo("Билет не найден!");
    }
}