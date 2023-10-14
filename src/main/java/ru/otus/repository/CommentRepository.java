package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment save(Comment comment);

    void deleteById(Long id);

    @Query("SELECT c FROM Comment c WHERE c.book.name = :name")
    List<Comment> findCommentsByBook(String name);

    @Query("SELECT c FROM Comment c WHERE c.book.id = :bookId")
    List<Comment> findCommentsByBookId(Long bookId);

}
