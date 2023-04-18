package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.Ticket;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

class Sql2oTicketRepositoryTest {
    static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    public static void initRepository() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oTicketRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        DataSourceConfiguration configuration = new DataSourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.dataBaseClient(dataSource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void clearTickets() {
        Collection<Ticket> tickets = sql2oTicketRepository.findAll();
        for (Ticket ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    @Test
    public void whenSaveTicketThenGetTheSame() {
        Ticket savedTicket = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1)).get();
        Ticket actualTicket = sql2oTicketRepository.findById(savedTicket.getId()).get();
        assertThat(actualTicket).isEqualTo(savedTicket);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        Ticket savedTicket1 = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1)).get();
        Ticket savedTicket2 = sql2oTicketRepository.save(new Ticket(2, 2, 2, 2, 2)).get();
        Ticket savedTicket3 = sql2oTicketRepository.save(new Ticket(3, 3, 3, 3, 3)).get();
        Collection<Ticket> tickets = sql2oTicketRepository.findAll();
        assertThat(tickets).isEqualTo(List.of(savedTicket1, savedTicket2, savedTicket3));
    }

    @Test
    public void whenSaveTicketsWithTheSameSessionsAndRowsThenEmptyOptional() {
        Ticket ticket = new Ticket(1, 1, 1, 1, 1);
        sql2oTicketRepository.save(ticket);
        assertThatThrownBy(() -> sql2oTicketRepository.save(new Ticket(2, 1, 1, 1, 1)))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        int findingId = 1;
        assertThat(sql2oTicketRepository.findById(findingId)).isEqualTo(Optional.empty());
        assertThat(sql2oTicketRepository.findAll()).isEqualTo(emptyList());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        Ticket savedTicket = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1)).get();
        boolean isDeleted = sql2oTicketRepository.deleteById(savedTicket.getId());
        assertThat(isDeleted).isTrue();
        assertThat(sql2oTicketRepository.findById(savedTicket.getId())).isEqualTo(Optional.empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        int deletingId = 1;
        assertThat(sql2oTicketRepository.deleteById(deletingId)).isFalse();
    }
}