package ru.otus.repository.impl;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jpa-based repository for working with authors")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    private static final int EXPECTED_QUERIES_COUNT = 1;

    @DisplayName("Should save author")
    @Test
    public void shouldSaveAuthor() {
        var expectedAuthor = new Author("Igor");
        repositoryJpa.save(expectedAuthor);
        var actualAuthor = em.find(Author.class, expectedAuthor.getId());
        Assertions.assertNotNull(actualAuthor);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
        Assertions.assertNotNull(actualAuthor.getId());
    }

    @DisplayName("Should find author by id")
    @Test
    public void shouldFindById() {
        var expectedAuthor = new Author(1L, "Stephen king");
        var actualAuthor = em.find(Author.class, expectedAuthor.getId());
        Assertions.assertNotNull(actualAuthor);
        Assertions.assertEquals(expectedAuthor, actualAuthor);
    }

    @DisplayName("Should find author by name")
    @Test
    public void shouldFindByName() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        var expectedAuthor = new Author(3L, "Robert Martin");
        var actualAuthors = repositoryJpa.findByName("Robert Martin");
        Assertions.assertEquals(1, actualAuthors.size());
        Assertions.assertEquals(expectedAuthor, actualAuthors.get(0));
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }
}
