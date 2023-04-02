package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketService {
    Optional<TicketDto> save(Ticket ticket);
    Optional<TicketDto> findById(int id);
    Collection<TicketDto> findAll();
    boolean deleteById(int id);
}