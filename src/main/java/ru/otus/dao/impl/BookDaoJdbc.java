package ru.otus.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.dao.BookDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Integer count() {
        return jdbc.queryForObject("select count(*) from book", Map.of(), Integer.class);
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into book (name, author_id, genre_id) values (:name, :author_id, :genre_id)",
                Map.of("name", book.getName(), "author_id", book.getAuthorId(),
                        "genre_id", book.getGenreId()));
    }

    @Override
    public void update(Book book) {
        jdbc.update("update book set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of("name", book.getName(), "author_id", book.getAuthorId(), "genre_id", book.getGenreId(),
                        "id", book.getId()));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("select b.id as book_id, " +
                        "b.name as book_name, " +
                        "b.author_id, " +
                        "b.genre_id," +
                        "a.name as author_name, " +
                        "g.name as genre_name " +
                        "from book b " +
                        "inner join author a ON a.id = b.author_id " +
                        "inner join genre g ON g.id = b.genre_id " +
                        "where b.id = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id as book_id, " +
                "b.name as book_name, " +
                "b.author_id, " +
                "b.genre_id, " +
                "a.name as author_name, " +
                "g.name as genre_name " +
                "from book b " +
                "inner join author a ON a.id = b.author_id " +
                "inner join genre g ON g.id = b.genre_id ", new BookMapper());
    }

    @Override
    public void delete(Long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    @Override
    public List<Book> getBooksByAuthor(String name) {
        return jdbc.query("select b.id as book_id, " +
                "       b.name as book_name, " +
                "       g.id as genre_id, " +
                "       g.name as genre_name, " +
                "       a.id as author_id, " +
                "       a.name as author_name " +
                "from author a " +
                "         inner join book b ON a.id = b.author_id " +
                "         left outer join genre g ON b.genre_id = g.id " +
                "where UPPER(a.name) like UPPER(:name)", Map.of("name", name), new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("book_id");
            String name = resultSet.getString("book_name");
            Long authorId = resultSet.getLong("author_id");
            Long genreId = resultSet.getLong("genre_id");
            String genreName = resultSet.getString("genre_name");
            String authorName = resultSet.getString("author_name");

            var book = new Book();
            var author = new Author(authorId, authorName);
            var genre = new Genre(genreId, genreName);

            book.setId(id);
            book.setName(name);
            book.setAuthor(author);
            book.setGenre(genre);
            return book;
        }

    }
}
