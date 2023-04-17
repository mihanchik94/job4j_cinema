package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;
    private final HallService hallService;

    public TicketController(TicketService ticketService, FilmSessionService filmSessionService, HallService hallService) {
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
        this.hallService = hallService;
    }

    @GetMapping("/buyTicketForm/{id}")
    public String buyTicketPage(@PathVariable("id") int id, Model model) {
        List<Integer> rows = new ArrayList<>();
        List<Integer> places = new ArrayList<>();
        Optional<FilmSessionDto> filmSession = filmSessionService.findById(id);
        Optional<Hall> hall = hallService.findById(filmSession.get().getHallId());
        for (int i = 1; i <= hall.get().getRowCount(); i++) {
            rows.add(i);
        }
        for (int i = 1; i <= hall.get().getPlaceCount(); i++) {
            places.add(i);
        }
        model.addAttribute("filmSession", filmSession.get());
        model.addAttribute("rows", rows);
        model.addAttribute("places", places);
        model.addAttribute("ticket", new Ticket());
        return "tickets/buyTicket";
    }

    @PostMapping("/buyTicket")
    public String buyTicket(@ModelAttribute("ticket") Ticket ticket, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ticket.setUserId(user.getId());
        Optional<TicketDto> optionalTicket = ticketService.save(ticket);
        if (optionalTicket.isEmpty()) {
            model.addAttribute( "error", "Билет с таким местом уже куплен! Попробуйте купить билет на другое место");
            return "errors/404";
        }
        return "redirect:/tickets/purchases/" + ticket.getId();
    }

    @GetMapping("/purchases/{id}")
    public String boughtTicketPage(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Optional<TicketDto> optionalTicket = ticketService.findById(id);
        if (optionalTicket.isEmpty() || user.getId() != optionalTicket.get().getUserId()) {
            model.addAttribute("error", "Билет не найден!");
            return "errors/404";
        }
        model.addAttribute("message", "Вы успешно приобрели билет на фильм " + optionalTicket.get().getFilmName()
                + " в " + optionalTicket.get().getHallName() + ". Ваш ряд " + optionalTicket.get().getRowNumber() + ", ваше место "
                + optionalTicket.get().getPlaceNumber() + " начало сеанса: " + optionalTicket.get().getStartTime());
        return "tickets/purchases";
    }
}