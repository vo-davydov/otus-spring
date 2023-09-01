package ru.otus.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.exception.BookNotFoundException;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public void save(Book book) {
        if (book.getId() == null || book.getId() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Book findById(long id) {
        var book = Optional.ofNullable(em.find(Book.class, id)).orElseThrow(BookNotFoundException::new);
        Hibernate.initialize(book.getAuthor());
        Hibernate.initialize(book.getGenre());
        return book;
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre",
                Book.class);
        return query.getResultList();
    }

    @Override
    public void delete(Long id) {
        var book = findByIdLazy(id);
        em.remove(book);
    }

    @Override
    public List<Book> findBooksByAuthor(String name) {
        TypedQuery<Book> query = em.createQuery("SELECT b " +
                        "FROM Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "WHERE UPPER(b.author.name) = UPPER(:name)",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooksByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "where UPPER(b.name) =  UPPER(:name)",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    private Book findByIdLazy(Long id) {
        return Optional.ofNullable(em.find(Book.class, id)).orElseThrow(BookNotFoundException::new);
    }
}
