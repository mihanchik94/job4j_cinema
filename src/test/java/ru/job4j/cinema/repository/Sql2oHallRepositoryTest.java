package ru.job4j.cinema.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Hall;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oHallRepositoryTest {
    static Sql2oHallRepository sql2oHallRepository;
    static Collection<Hall> expectedHalls;

    @BeforeAll
    public static void initRepository() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oHallRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        DataSourceConfiguration configuration = new DataSourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.dataBaseClient(dataSource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @BeforeAll
    public static void fillExpectedHalls() {
        Hall hall1 = new Hall(1, "Зал 1", 5, 20, "Зал №1");
        Hall hall2 = new Hall(2, "Зал 2", 5, 20, "Зал №2");
        Hall hall3 = new Hall(3, "Зал 3", 5, 20, "Зал №3");
        Hall hall4 = new Hall(4, "Зал 4", 5, 20, "Зал №4");
        Hall hall5 = new Hall(5, "Зал 5", 5, 20, "Зал №5");
        expectedHalls = List.of(hall1, hall2, hall3, hall4, hall5);
    }

    @Test
    public void whenFindByIdThenGetHall() {
        int expectedHallId = 4;
        Hall expectedHall = new Hall(4, "Зал 4", 5, 20, "Зал №4");
        Hall actualHall = sql2oHallRepository.findById(expectedHallId).get();
        Collection<Hall> actualHalls = sql2oHallRepository.findAll();
        assertThat(actualHall).isEqualTo(expectedHall);
        assertThat(actualHalls).isEqualTo(expectedHalls);
    }

    @Test
    public void whenFindNonExistedHallThenEmptyOptional() {
        assertThat(sql2oHallRepository.findById(7)).isEqualTo(Optional.empty());
    }
}