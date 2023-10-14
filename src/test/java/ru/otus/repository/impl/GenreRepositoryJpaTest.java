package ru.otus.repository.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.domain.Genre;
import ru.otus.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Jpa-based repository for working with genre")
@DataJpaTest
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @Test
    void save() {
        var actualGenre = new Genre(1L, "test");
        repositoryJpa.save(actualGenre);
        assertNotNull(actualGenre.getId());
        var expectedGenre = em.find(Genre.class, 1L);
        assertNotNull(actualGenre);
        assertThat(actualGenre)
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void findById() {
        var expectedGenre = em.find(Genre.class, 1L);
        var actualGenre = repositoryJpa.findById(1L);
        assertThat(actualGenre).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void findByName() {
        var expectedGenre = em.find(Genre.class, 1L);
        var actualGenre = repositoryJpa.findByName("horror");
        assertNotNull(actualGenre);
        assertEquals(expectedGenre, actualGenre);
    }
}