package ru.otus.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final long EXISTING_GENRE_ID = 1L;

    private static final String EXISTING_GENRE_NAME = "psychology";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

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

}