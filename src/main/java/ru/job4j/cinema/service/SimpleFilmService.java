package ru.job4j.cinema.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@ThreadSafe
@Service
public class SimpleFilmService implements FilmService {
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreRepository sql2oGenreRepository) {
        this.filmRepository = sql2oFilmRepository;
        this.genreRepository = sql2oGenreRepository;
    }

    @Override
    public Optional<FilmDto> findById(int id) {
        Optional<Film> optionalFilm = filmRepository.findById(id);
        if (optionalFilm.isEmpty()) {
            return Optional.empty();
        }
        Optional<Genre> optionalGenre = genreRepository.findById(optionalFilm.get().getGenreId());
        if (optionalGenre.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new FilmDto(optionalFilm.get().getId(), optionalFilm.get().getName(), optionalFilm.get().getDescription(),
                optionalFilm.get().getYear(), optionalFilm.get().getMinimalAge(),
                optionalFilm.get().getDurationInMinutes(), optionalGenre.get().getName()));
    }

    @Override
    public Collection<FilmDto> findAll() {
        Collection<FilmDto> result = new ArrayList<>();
        Collection<Film> films = filmRepository.findAll();
        for (Film film : films) {
            Optional<Genre> optionalGenre = genreRepository.findById(film.getGenreId());
            result.add(new FilmDto(film.getId(), film.getName(), film.getDescription(), film.getYear(), film.getMinimalAge(),
                    film.getDurationInMinutes(), optionalGenre.get().getName()));
        }
        return result;
    }
}