package ru.otus.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.User;
import ru.otus.repository.UserRepository;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/users/create")
    public User createUser(@RequestBody User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

}
