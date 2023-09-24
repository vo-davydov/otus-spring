package ru.otus.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Comment;
import ru.otus.exception.CommentNotFoundException;
import ru.otus.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null || comment.getId() <= 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public void delete(Long id) {
        var comment = findById(id).orElseThrow(CommentNotFoundException::new);
        em.remove(comment);
    }

    @Override
    public List<Comment> findCommentsByBook(String name) {
        var query = em.createQuery(
                "SELECT c " +
                        "FROM Comment c " +
                        "WHERE UPPER(c.book.name) = UPPER(:name)",
                Comment.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Comment> findCommentsByBookId(Long bookId) {
        var query = em.createQuery("SELECT c " +
                        "FROM Comment c " +
                        "WHERE c.book.id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
