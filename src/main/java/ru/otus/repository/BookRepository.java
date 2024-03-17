package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b join fetch b.author join fetch b.genre")
    List<Book> findAll();

    @PreAuthorize("hasPermission('#id','ru.otus.domain.Book', 'DELETE')")
    void deleteById(Long id);

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Query("SELECT b FROM Book b join fetch b.author join fetch b.genre WHERE UPPER(b.author.name) = UPPER(:name)")
    List<Book> findBooksByAuthorName(String name);

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findBooksByName(String name);

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @EntityGraph(attributePaths = {"author", "genre"})
    Optional<Book> findById(Long id);

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @Query("SELECT b FROM Book b where b.id = :id")
    Optional<Book> findByIdLazy(Long id);

}
