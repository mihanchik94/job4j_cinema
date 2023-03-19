package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleFilmSessionService implements FilmSessionService {
    private final FilmSessionRepository filmSessionRepository;
    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository, FilmRepository sql2oFilmRepository,
                                    HallRepository sql2oHallRepository) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmRepository = sql2oFilmRepository;
        this.hallRepository = sql2oHallRepository;
    }

    @Override
    public Optional<FilmSessionDto> findById(int id) {
        Optional<FilmSession> optionalFilmSession = filmSessionRepository.findById(id);
        if (optionalFilmSession.isEmpty()) {
            return Optional.empty();
        }
        Optional<Film> optionalFilm = filmRepository.findById(optionalFilmSession.get().getFilmId());
        Optional<Hall> optionalHall = hallRepository.findById(optionalFilmSession.get().getHallId());
        if (optionalFilm.isEmpty() || optionalHall.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new FilmSessionDto(optionalFilm.get().getName(), optionalHall.get().getName(),
                optionalFilmSession.get().getStartTime(), optionalFilmSession.get().getEndTime(), optionalFilmSession.get().getPrice()));
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        Collection<FilmSessionDto> result = new ArrayList<>();
        Collection<FilmSession> filmSessions = filmSessionRepository.findAll();
        for (FilmSession filmSession : filmSessions) {
            Optional<Film> optionalFilm = filmRepository.findById(filmSession.getFilmId());
            Optional<Hall> optionalHall = hallRepository.findById(filmSession.getHallId());
            result.add(new FilmSessionDto(optionalFilm.get().getName(), optionalHall.get().getName(),
                    filmSession.getStartTime(), filmSession.getEndTime(), filmSession.getPrice()));
        }
        return result;
    }
}