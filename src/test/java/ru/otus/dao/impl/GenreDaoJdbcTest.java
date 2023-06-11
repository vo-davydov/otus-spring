package ru.otus.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRE_COUNT = 3;

    private static final long EXISTING_GENRE_ID = 1L;

    private static final String EXISTING_GENRE_NAME = "psychology";

    private static final long EXISTING_GENRE_ID_2 = 2L;

    private static final String EXISTING_GENRE_NAME_2 = "economy";

    private static final long EXISTING_GENRE_ID_3 = 3L;

    private static final String EXISTING_GENRE_NAME_3 = "adventures";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void shouldReturnExpectedGenreCount() {
        Integer actualGenreCount = genreDaoJdbc.count();
        assertNotNull(actualGenreCount);
        assertThat(actualGenreCount).isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    void shouldInsertGenre() {
        Genre expectedGenre = new Genre(4L, "Sci-Fi");
        genreDaoJdbc.insert(expectedGenre);
        Genre actualGenre = genreDaoJdbc.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDaoJdbc.getById(expectedGenre.getId());
        assertEquals(actualGenre.getName(), actualGenre.getName());
        assertEquals(actualGenre.getId(), actualGenre.getId());
    }

    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> authors = genreDaoJdbc.getAll();
        assertNotNull(authors);
        assertEquals(authors.size(), 3);
        var authorMap = authors.stream().collect(Collectors.toMap(
                Genre::getId,
                Genre::getName));

        assertEquals(authorMap.get(EXISTING_GENRE_ID), EXISTING_GENRE_NAME);
        assertEquals(authorMap.get(EXISTING_GENRE_ID_2), EXISTING_GENRE_NAME_2);
        assertEquals(authorMap.get(EXISTING_GENRE_ID_3), EXISTING_GENRE_NAME_3);
    }
}