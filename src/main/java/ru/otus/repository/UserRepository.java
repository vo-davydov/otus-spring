package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
