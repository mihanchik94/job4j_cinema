package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.FilmSessionService;

@ThreadSafe
@Controller
public class FilmSessionController {
    private final FilmSessionService filmSessionService;

    public FilmSessionController(FilmSessionService filmSessionService) {
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/sessions")
    public String getAll(Model model) {
        model.addAttribute("filmSessions", filmSessionService.findAll());
        return "sessions/list";
    }
}