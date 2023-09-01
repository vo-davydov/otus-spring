package ru.otus.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.repository.BookRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long count() {
        return (long) em.createNativeQuery("select count(*) from Book").getSingleResult();
    }

    @Override
    @Transactional
    public void save(Book book) {
        if (book.getId() == null || book.getId() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                        "left join fetch b.author left join fetch b.comments",
                Book.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> findBooksByAuthor(String name) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "         inner join Author a on b.author.id = a.id " +
                        "where UPPER(a.name) = UPPER(:name)",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Book> findBooksByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "where UPPER(b.name) =  UPPER(:name)",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
