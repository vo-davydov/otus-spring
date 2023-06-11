package ru.otus.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с авторами должно")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHOR_COUNT = 3;

    private static final long EXISTING_AUTHOR_ID = 1L;

    private static final String EXISTING_AUTHOR_NAME = "Jack London";

    private static final long EXISTING_AUTHOR_ID_2 = 2L;

    private static final String EXISTING_AUTHOR_NAME_2 = "Jordan Peterson";

    private static final long EXISTING_AUTHOR_ID_3 = 3L;

    private static final String EXISTING_AUTHOR_NAME_3 = "Daniel Kahneman";

    @Autowired
    private AuthorDaoJdbc authorDaoJdbc;

    @Test
    void shouldReturnExpectedBookCount() {
        Integer actualAuthorCount = authorDaoJdbc.count();
        assertNotNull(actualAuthorCount);
        assertThat(actualAuthorCount).isEqualTo(EXPECTED_AUTHOR_COUNT);
    }

    @Test
    void shouldInsertAuthor() {
        Author expectedAuthor = new Author(4L, "Igor");
        authorDaoJdbc.insert(expectedAuthor);
        Author actualAuthor = authorDaoJdbc.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    void shouldReturnExpectedAuthorById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDaoJdbc.getById(expectedAuthor.getId());
        assertEquals(actualAuthor.getName(), expectedAuthor.getName());
        assertEquals(actualAuthor.getId(), expectedAuthor.getId());
    }

    @Test
    void shouldReturnExpectedAuthorList() {
        List<Author> authors = authorDaoJdbc.getAll();
        assertNotNull(authors);
        assertEquals(authors.size(), 3);
        var authorMap = authors.stream().collect(Collectors.toMap(
                Author::getId,
                Author::getName));

        assertEquals(authorMap.get(EXISTING_AUTHOR_ID), EXISTING_AUTHOR_NAME);
        assertEquals(authorMap.get(EXISTING_AUTHOR_ID_2), EXISTING_AUTHOR_NAME_2);
        assertEquals(authorMap.get(EXISTING_AUTHOR_ID_3), EXISTING_AUTHOR_NAME_3);
    }
}