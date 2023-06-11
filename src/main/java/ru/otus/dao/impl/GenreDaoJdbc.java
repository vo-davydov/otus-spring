package ru.otus.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Integer count() {
        return jdbc.queryForObject("select count(*) from genre", Map.of(), Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into genre (id, name) values (:id, :name)",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("select id, name from genre where id = :id",
                Map.of("id", id), new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select * from genre", new GenreMapper());
    }

    public Genre getByName(String name) {
        return jdbc.queryForObject("select id, name from genre where UPPER(name) LIKE UPPER(:name)",
                Map.of("name", name), new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            var genre = new Genre();
            genre.setId(id);
            genre.setName(name);
            return genre;
        }

    }

}
