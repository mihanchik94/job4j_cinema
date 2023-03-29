package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketService {
    Ticket save(Ticket ticket);
    Optional<Ticket> findById(int id);
    Collection<Ticket> findAll();
    boolean deleteById(int id);
}