package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Genre;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oGenreRepositoryTest {
    static Sql2oGenreRepository sql2oGenreRepository;
    static Collection<Genre> expectedGenres;

    @BeforeAll
    public static void initRepository() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFilmRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        DataSourceConfiguration configuration = new DataSourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.dataBaseClient(dataSource);

        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);
    }

    @BeforeAll
    public static void fillExpectedGenres() {
        Genre genre1 = new Genre(1, "Драма");
        Genre genre2 = new Genre(2, "Комедия");
        Genre genre3 = new Genre(3, "Триллер");
        Genre genre4 = new Genre(4, "Боевик");
        Genre genre5 = new Genre(5, "Ужасы");
        expectedGenres = List.of(genre1, genre2, genre3, genre4, genre5);
    }

    @Test
    public void whenFindByIdThenGetGenre() {
        int expectedGenreId = 2;
        Genre expectedGenre = new Genre(2, "Комедия");
        Genre actualGenre = sql2oGenreRepository.findById(expectedGenreId).get();
        Collection<Genre> actualGenres = sql2oGenreRepository.findAll();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
        assertThat(actualGenres).isEqualTo(expectedGenres);
    }

    @Test
    public void whenFindNonExistedGenreThenEmptyOptional() {
        assertThat(sql2oGenreRepository.findById(7)).isEqualTo(Optional.empty());
    }
}