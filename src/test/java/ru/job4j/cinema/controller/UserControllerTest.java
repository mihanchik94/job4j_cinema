package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {
    private UserService userService;
    private UserController userController;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void initServices() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void whenPostRegisterUserSuccessfullyThenRedirectToFilms() {
        User user = new User(1, "test", "test", "test");
        when(userService.save(user)).thenReturn(Optional.of(user));

        ConcurrentModel model = new ConcurrentModel();
        String view = userController.register(user, model, request);

        assertThat(view).isEqualTo("redirect:/films");
    }

    @Test
    public void whenPostRegisterUserAndFailThenRedirectToErrors() {
        User user = new User(1, "test", "test", "test");
        when(userService.save(any())).thenReturn(Optional.empty());

        ConcurrentModel model = new ConcurrentModel();
        String view = userController.register(user, model, request);
        Object expectedMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("errors/404");
        assertThat(expectedMessage).isEqualTo("Пользователь с такой почтой уже существует");
    }

    @Test
    public void whenPostLoginUserSuccessfullyThenRedirectToFilms() {
        User user = new User(1, "test", "test", "test");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        ConcurrentModel model = new ConcurrentModel();
        String view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("redirect:/films");
    }

    @Test
    public void whenPostLoginUserAndFailThenRedirectToFilms() {
        User user = new User(1, "test", "test", "test");
        when(userService.save(any())).thenReturn(Optional.empty());

        ConcurrentModel model = new ConcurrentModel();
        String view = userController.loginUser(user, model, request);
        Object expectedMessage = model.getAttribute("error");

        assertThat(view).isEqualTo("errors/404");
        assertThat(expectedMessage).isEqualTo("Почта или пароль введены неверно");
    }
}