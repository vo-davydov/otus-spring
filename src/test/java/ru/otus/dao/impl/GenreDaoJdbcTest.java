package ru.otus.dao.impl;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Dao для работы с жанрами должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final long EXISTING_GENRE_ID = 1L;

    private static final String EXISTING_GENRE_NAME = "horror";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @Test
    void shouldInsertAuthor() {
        Genre expectedGenre = new Genre(4L, "comics");
        genreDaoJdbc.insert(expectedGenre);

        var actualGenre = genreDaoJdbc.getByName("comics");
        AssertionsForClassTypes.assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    void shouldReturnExpectedGenreByName() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDaoJdbc.getByName(EXISTING_GENRE_NAME);
        assertEquals(actualGenre.getName(), expectedGenre.getName());
        assertEquals(actualGenre.getId(), expectedGenre.getId());
    }

}