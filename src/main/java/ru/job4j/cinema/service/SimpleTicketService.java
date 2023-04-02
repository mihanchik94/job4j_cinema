package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.TicketDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleTicketService implements TicketService {
    private final TicketRepository ticketRepository;
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository, FilmSessionRepository sql2oFilmSessionRepository,
                               FilmRepository sql2oFilmRepository, HallRepository sql2oHallRepository) {
        this.ticketRepository = sql2oTicketRepository;
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmRepository = sql2oFilmRepository;
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Optional<TicketDto> save(Ticket ticket) {
        Optional<Ticket> optionalTicket = ticketRepository.save(ticket);
        if (optionalTicket.isEmpty()) {
            return Optional.empty();
        }
        Optional<FilmSession> optionalFilmSession = filmSessionRepository.findById(optionalTicket.get().getSessionId());
        if (optionalFilmSession.isEmpty()) {
            return Optional.empty();
        }
        Optional<Film> optionalFilm = filmRepository.findById(optionalFilmSession.get().getFilmId());
        Optional<Hall> optionalHall = hallRepository.findById(optionalFilmSession.get().getHallId());
        if (optionalFilm.isEmpty() || optionalHall.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TicketDto(ticket.getSessionId(), optionalFilm.get().getName(), optionalHall.get().getName(),
                ticket.getRowNumber(), ticket.getPlaceNumber(), optionalFilmSession.get().getStartTime(),
                optionalFilmSession.get().getEndTime()));
    }

    @Override
    public Optional<TicketDto> findById(int id) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isEmpty()) {
            return Optional.empty();
        }
        Optional<FilmSession> optionalFilmSession = filmSessionRepository.findById(optionalTicket.get().getSessionId());
        if (optionalFilmSession.isEmpty()) {
            return Optional.empty();
        }
        Optional<Film> optionalFilm = filmRepository.findById(optionalFilmSession.get().getFilmId());
        Optional<Hall> optionalHall = hallRepository.findById(optionalFilmSession.get().getHallId());
        if (optionalFilm.isEmpty() || optionalHall.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new TicketDto(optionalTicket.get().getSessionId(), optionalFilm.get().getName(), optionalHall.get().getName(),
                optionalTicket.get().getRowNumber(), optionalTicket.get().getPlaceNumber(), optionalFilmSession.get().getStartTime(),
                optionalFilmSession.get().getEndTime()));
    }

    @Override
    public Collection<TicketDto> findAll() {
        Collection<TicketDto> result = new ArrayList<>();
        Collection<Ticket> tickets = ticketRepository.findAll();
        for (Ticket ticket : tickets) {
            Optional<FilmSession> optionalFilmSession = filmSessionRepository.findById(ticket.getSessionId());
            Optional<Film> optionalFilm = filmRepository.findById(optionalFilmSession.get().getFilmId());
            Optional<Hall> optionalHall = hallRepository.findById(optionalFilmSession.get().getHallId());
            result.add(new TicketDto(ticket.getSessionId(), optionalFilm.get().getName(), optionalHall.get().getName(),
                    ticket.getRowNumber(), ticket.getPlaceNumber(), optionalFilmSession.get().getStartTime(),
                    optionalFilmSession.get().getEndTime()));
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        return ticketRepository.deleteById(id);
    }
}