package ru.otus.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Jpa-based repository for working with genre")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository repositoryJpa;

    @Test
    void save() {
        var expectedGenre = new Genre("detective");
        repositoryJpa.save(expectedGenre);
        assertNotNull(expectedGenre.getId());
        var actualGenre = repositoryJpa.findById(expectedGenre.getId());
        assertNotNull(actualGenre.get());
        assertEquals(expectedGenre, actualGenre.get());
    }

    @Test
    void findById() {
        var expectedGenre = new Genre(1L, "horror");
        var actualGenre = repositoryJpa.findById(1L);
        assertNotNull(actualGenre.get());
        assertEquals(expectedGenre, actualGenre.get());
    }

    @Test
    void findByName() {
        var expectedGenre = new Genre(1L, "horror");
        var actualGenre = repositoryJpa.findByName("horror");
        assertNotNull(actualGenre);
        assertEquals(expectedGenre, actualGenre);
    }
}