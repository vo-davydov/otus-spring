package ru.otus.repository.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa-based repository for working with authors")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @DisplayName("Should save author")
    @Test
    public void shouldSaveAuthor() {
        var expectedAuthor = new Author("Igor");
        repositoryJpa.save(expectedAuthor);
        var actualAuthor = repositoryJpa.findById(expectedAuthor.getId());
        Assertions.assertNotNull(actualAuthor.get());
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(expectedAuthor);
        Assertions.assertNotNull(actualAuthor.get().getId());
    }

    @DisplayName("Should find author by id")
    @Test
    public void shouldFindById() {
        var expectedAuthor = new Author(1L, "Stephen king");
        var actualAuthor = repositoryJpa.findById(expectedAuthor.getId());
        Assertions.assertNotNull(actualAuthor.get());
        Assertions.assertEquals(expectedAuthor, actualAuthor.get());
    }

    @DisplayName("Should find author by name")
    @Test
    public void shouldFindByName() {
        var expectedAuthor = new Author(3L, "Robert Martin");
        var actualAuthors = repositoryJpa.findByName("Robert Martin");
        Assertions.assertEquals(1, actualAuthors.size());
        Assertions.assertEquals(expectedAuthor, actualAuthors.get(0));
    }
}
