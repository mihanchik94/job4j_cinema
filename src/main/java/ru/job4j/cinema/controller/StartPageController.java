package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@ThreadSafe
@Controller
public class StartPageController {
    @GetMapping("/")
    public String getStartPage() {
        return "start";
    }
}