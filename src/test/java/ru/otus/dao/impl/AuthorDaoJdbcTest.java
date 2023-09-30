package ru.otus.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final long EXISTING_AUTHOR_ID = 1L;

    private static final String EXISTING_AUTHOR_NAME = "Stephen king";

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author("Igor");
        authorDaoJdbc.insert(expectedAuthor);
        expectedAuthor.setId(4L);
        Author actualAuthor = authorDaoJdbc.getById(4L);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDaoJdbc.getById(expectedAuthor.getId());
        assertEquals(actualAuthor.getName(), expectedAuthor.getName());
        assertEquals(actualAuthor.getId(), expectedAuthor.getId());
    }

}