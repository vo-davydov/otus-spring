package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join fetch b.author join fetch b.genre")
    List<Book> findAll();

    void deleteById(Long id);

    @Query("SELECT b FROM Book b join fetch b.author join fetch b.genre WHERE UPPER(b.author.name) = UPPER(:name)")
    List<Book> findBooksByAuthorName(String name);

    List<Book> findBooksByName(String name);

    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(Long id);


    @Query("SELECT b FROM Book b where b.id = :id")
    Optional<Book> findByIdLazy(Long id);

}
