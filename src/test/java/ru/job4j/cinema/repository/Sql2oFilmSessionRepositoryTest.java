package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.FilmSession;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmSessionRepositoryTest {
    static Sql2oFilmSessionRepository sql2oFilmSessionRepository;
    static Collection<FilmSession> expectedFilmSessions;

    @BeforeAll
    public static void initRepository() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oFilmSessionRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        DataSourceConfiguration configuration = new DataSourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.dataBaseClient(dataSource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @BeforeAll
    public static void fillExpectedFilmSessions() {
        FilmSession filmSession1 = new FilmSession (1,1, 1, LocalDateTime.of(2022, 8, 30, 8, 0),
                LocalDateTime.of(2022, 8, 30, 10, 0), 200);
        FilmSession filmSession2 = new FilmSession (2,1, 1, LocalDateTime.of(2022, 8, 30, 10, 30),
                LocalDateTime.of(2022, 8, 30, 12, 30), 350);
        FilmSession filmSession3 = new FilmSession (3,2, 1, LocalDateTime.of(2022, 8, 30, 13, 0),
                LocalDateTime.of(2022, 8, 30, 15, 0), 400);
        FilmSession filmSession4 = new FilmSession (4,2, 1, LocalDateTime.of(2022, 8, 30, 15, 30),
                LocalDateTime.of(2022, 8, 30, 17, 30), 400);
        FilmSession filmSession5 = new FilmSession (5,3, 1, LocalDateTime.of(2022, 8, 30, 18, 0),
                LocalDateTime.of(2022, 8, 30, 19, 20), 400);
        FilmSession filmSession6 = new FilmSession (6,3, 1, LocalDateTime.of(2022, 8, 30, 19, 50),
                LocalDateTime.of(2022, 8, 30, 21, 10), 400);
        FilmSession filmSession7 = new FilmSession (7,4, 1, LocalDateTime.of(2022, 8, 30, 21, 40),
                LocalDateTime.of(2022, 8, 30, 23, 20), 400);
        FilmSession filmSession8 = new FilmSession (8,4, 1, LocalDateTime.of(2022, 8, 30, 23, 50),
                LocalDateTime.of(2022, 8, 31, 1, 30), 400);
        FilmSession filmSession9 = new FilmSession (9,5, 2, LocalDateTime.of(2022, 8, 30, 8, 0),
                LocalDateTime.of(2022, 8, 30, 10, 35), 200);
        FilmSession filmSession10 = new FilmSession (10,5, 2, LocalDateTime.of(2022, 8, 30, 10, 50),
                LocalDateTime.of(2022, 8, 30, 13, 35), 350);
        FilmSession filmSession11 = new FilmSession (11,6, 2, LocalDateTime.of(2022, 8, 30, 13, 50),
                LocalDateTime.of(2022, 8, 30, 15, 10), 400);
        FilmSession filmSession12 = new FilmSession (12,6, 2, LocalDateTime.of(2022, 8, 30, 15, 30),
                LocalDateTime.of(2022, 8, 30, 17, 30), 400);
        FilmSession filmSession13 = new FilmSession (13,7, 2, LocalDateTime.of(2022, 8, 30, 18, 0),
                LocalDateTime.of(2022, 8, 30, 19, 40), 400);
        FilmSession filmSession14 = new FilmSession (14,7, 2, LocalDateTime.of(2022, 8, 30, 20, 0),
                LocalDateTime.of(2022, 8, 30, 21, 40), 400);
        FilmSession filmSession15 = new FilmSession (15,8, 2, LocalDateTime.of(2022, 8, 30, 22, 0),
                LocalDateTime.of(2022, 8, 31, 0, 35), 400);
        FilmSession filmSession16 = new FilmSession (16,8, 2, LocalDateTime.of(2022, 8, 31, 0, 50),
                LocalDateTime.of(2022, 8, 31, 3, 25), 250);
        FilmSession filmSession17 = new FilmSession (17,9, 3, LocalDateTime.of(2022, 8, 30, 8, 0),
                LocalDateTime.of(2022, 8, 30, 10, 30), 250);
        FilmSession filmSession18 = new FilmSession (18,9, 3, LocalDateTime.of(2022, 8, 30, 11, 0),
                LocalDateTime.of(2022, 8, 30, 13, 30), 350);
        FilmSession filmSession19 = new FilmSession (19,10, 3, LocalDateTime.of(2022, 8, 30, 14, 0),
                LocalDateTime.of(2022, 8, 30, 16, 30), 400);
        FilmSession filmSession20 = new FilmSession (20,10, 3, LocalDateTime.of(2022, 8, 30, 17, 0),
                LocalDateTime.of(2022, 8, 30, 19, 30), 400);
        FilmSession filmSession21 = new FilmSession (21,11, 3, LocalDateTime.of(2022, 8, 30, 20, 0),
                LocalDateTime.of(2022, 8, 30, 21, 40), 400);
        FilmSession filmSession22 = new FilmSession (22,11, 3, LocalDateTime.of(2022, 8, 30, 22, 0),
                LocalDateTime.of(2022, 8, 30, 23, 40), 400);
        FilmSession filmSession23 = new FilmSession (23,12, 3, LocalDateTime.of(2022, 8, 31, 0, 0),
                LocalDateTime.of(2022, 8, 31, 1, 30), 400);
        FilmSession filmSession24 = new FilmSession (24,12, 3, LocalDateTime.of(2022, 8, 31, 1, 50),
                LocalDateTime.of(2022, 8, 31, 3, 20), 250);
        FilmSession filmSession25 = new FilmSession (25,13, 4, LocalDateTime.of(2022, 8, 30, 8, 0),
                LocalDateTime.of(2022, 8, 30, 10, 0), 200);
        FilmSession filmSession26 = new FilmSession (26,13, 4, LocalDateTime.of(2022, 8, 30, 10, 20),
                LocalDateTime.of(2022, 8, 30, 12, 30), 350);
        FilmSession filmSession27 = new FilmSession (27,14, 4, LocalDateTime.of(2022, 8, 30, 13, 0),
                LocalDateTime.of(2022, 8, 30, 15, 0), 400);
        FilmSession filmSession28 = new FilmSession (28,14, 4, LocalDateTime.of(2022, 8, 30, 15, 30),
                LocalDateTime.of(2022, 8, 30, 17, 30), 400);
        FilmSession filmSession29 = new FilmSession (29,15, 4, LocalDateTime.of(2022, 8, 30, 18, 0),
                LocalDateTime.of(2022, 8, 30, 19, 40), 400);
        FilmSession filmSession30 = new FilmSession (30,15, 4, LocalDateTime.of(2022, 8, 30, 20, 0),
                LocalDateTime.of(2022, 8, 30, 21, 40), 400);
        FilmSession filmSession31 = new FilmSession (31,16, 4, LocalDateTime.of(2022, 8, 30, 22, 0),
                LocalDateTime.of(2022, 8, 31, 0, 10), 400);
        FilmSession filmSession32 = new FilmSession (32,16, 4, LocalDateTime.of(2022, 8, 31, 0, 30),
                LocalDateTime.of(2022, 8, 31, 2, 40), 400);
        FilmSession filmSession33 = new FilmSession (33,17, 5, LocalDateTime.of(2022, 8, 30, 8, 0),
                LocalDateTime.of(2022, 8, 30, 10, 10), 200);
        FilmSession filmSession34 = new FilmSession (34,17, 5, LocalDateTime.of(2022, 8, 30, 10, 40),
                LocalDateTime.of(2022, 8, 30, 12, 50), 350);
        FilmSession filmSession35 = new FilmSession (35,18, 5, LocalDateTime.of(2022, 8, 30, 13, 20),
                LocalDateTime.of(2022, 8, 30, 15, 0), 400);
        FilmSession filmSession36 = new FilmSession (36,18, 5, LocalDateTime.of(2022, 8, 30, 15, 20),
                LocalDateTime.of(2022, 8, 30, 17, 0), 400);
        FilmSession filmSession37 = new FilmSession (37,19, 5, LocalDateTime.of(2022, 8, 30, 17, 20),
                LocalDateTime.of(2022, 8, 30, 20, 10), 400);
        FilmSession filmSession38 = new FilmSession (38, 20, 5, LocalDateTime.of(2022, 8, 30, 20, 30),
                LocalDateTime.of(2022, 8, 30, 22, 40), 400);
        FilmSession filmSession39 = new FilmSession (39, 21, 5, LocalDateTime.of(2022, 8, 30, 23, 0),
                LocalDateTime.of(2022, 8, 31, 0, 40), 400);
        FilmSession filmSession40 = new FilmSession (40,21, 5, LocalDateTime.of(2022, 8, 31, 1, 10),
                LocalDateTime.of(2022, 8, 31, 2, 50), 400);

        expectedFilmSessions = List.of(filmSession1, filmSession2, filmSession3, filmSession4, filmSession5, filmSession6, filmSession7,
                filmSession8, filmSession9, filmSession10, filmSession11, filmSession12, filmSession13, filmSession14, filmSession15, filmSession16,
                filmSession17, filmSession18, filmSession19, filmSession20, filmSession21, filmSession22, filmSession23, filmSession24, filmSession25,
                filmSession26, filmSession27, filmSession28, filmSession29, filmSession30, filmSession31, filmSession32, filmSession33, filmSession34,
                filmSession35, filmSession36, filmSession37, filmSession38, filmSession39, filmSession40);
    }

    @Test
    public void whenFindByIdThenGetFilmSession() {
        int expectedFilmSessionId = 12;
        FilmSession expectedFilmSession = new FilmSession (12,6, 2, LocalDateTime.of(2022, 8, 30, 15, 30),
                LocalDateTime.of(2022, 8, 30, 17, 30), 400);
        FilmSession actualFilmSession = sql2oFilmSessionRepository.findById(expectedFilmSessionId).get();
        Collection<FilmSession> actualFilmSessions = sql2oFilmSessionRepository.findAll();
        assertThat(actualFilmSession).isEqualTo(expectedFilmSession);
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessions);
    }

    @Test
    public void whenFindNonExistedFilmSessionThenEmptyOptional() {
        assertThat(sql2oFilmSessionRepository.findById(66)).isEqualTo(Optional.empty());
    }
}