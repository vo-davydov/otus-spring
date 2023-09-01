package ru.otus.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Comment;
import ru.otus.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    @Transactional
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
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Query query = em.createQuery("delete " +
                "from Comment c " +
                "where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Comment> findCommentsByBook(String name) {
        var query = em.createNativeQuery("select c.id, c.text, c.book_id " +
                        "from Comment c " +
                        "where c.book_id in (select b.id from Book b where UPPER(b.name) = UPPER(:name))",
                Comment.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Comment> findCommentsByBookId(Long bookId) {
        var query = em.createNativeQuery("select c.id, c.text, c.book_id " +
                        "from Comment c " +
                        "where c.book_id = :bookId",
                Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
