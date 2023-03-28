package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DataSourceConfiguration;
import ru.job4j.cinema.model.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

class Sql2oUserRepositoryTest {
    static Sql2oUserRepository sql2oUserRepository;

    @BeforeAll
    public static void initRepository() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Sql2oUserRepository.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        String url = properties.getProperty("database.url");
        String username = properties.getProperty("database.username");
        String password = properties.getProperty("database.password");

        DataSourceConfiguration configuration = new DataSourceConfiguration();
        DataSource dataSource = configuration.connectionPool(url, username, password);
        Sql2o sql2o = configuration.dataBaseClient(dataSource);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void clearUsers() {
        Collection<User> users = sql2oUserRepository.findAll();
        for (User user : users) {
            sql2oUserRepository.deleteById(user.getId());
        }
    }

    @Test
    public void whenSaveUserThenGetTheSame() {
        User savedUser = sql2oUserRepository.save(new User(1, "name", "email", "password")).get();
        User actualUser = sql2oUserRepository.findByEmailAndPassword(savedUser.getEmail(), savedUser.getPassword()).get();
        assertThat(actualUser).isEqualTo(savedUser);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        User user1 = sql2oUserRepository.save(new User(1, "name1", "email1", "password1")).get();
        User user2 = sql2oUserRepository.save(new User(2, "name2", "email2", "password2")).get();
        User user3 = sql2oUserRepository.save(new User(3, "name3", "email3", "password3")).get();
        Collection<User> actualUsers = sql2oUserRepository.findAll();
        assertThat(actualUsers).isEqualTo(List.of(user1, user2, user3));
    }

    @Test
    public void whenSaveUsersWithTheSameEmailsAndPasswordThenException() {
        User user = new User(1, "name1", "email1", "password1");
        sql2oUserRepository.save(user);
        assertThatThrownBy(
                () -> sql2oUserRepository.save(new User(2, "name1", "email1", "password1")))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    public void whenDontSaveThenNothingFound() {
        String findingEmail = "email";
        String findingPassword = "password";
        assertThat(sql2oUserRepository.findByEmailAndPassword(findingEmail, findingPassword)).isEqualTo(Optional.empty());
        assertThat(sql2oUserRepository.findAll()).isEqualTo(emptyList());
    }

    @Test
    public void whenDeleteThenGetEmptyOptional() {
        User savedUser = sql2oUserRepository.save(new User(1, "name", "email", "password")).get();
        boolean isDeleted = sql2oUserRepository.deleteById(savedUser.getId());
        assertThat(isDeleted).isTrue();
        assertThat(sql2oUserRepository.findByEmailAndPassword(savedUser.getEmail(), savedUser.getPassword())).isEqualTo(Optional.empty());
    }

    @Test
    public void whenDeleteByInvalidIdThenGetFalse() {
        int deletingId = 1;
        assertThat(sql2oUserRepository.deleteById(deletingId)).isFalse();
    }
}