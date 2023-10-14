package ru.otus.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Comment;
import ru.otus.service.OutputService;
import ru.otus.service.PrintService;

@Service
@AllArgsConstructor
public class CommentPrintService implements PrintService<Comment> {

    private final OutputService outputService;

    @Override
    public void print(Comment comment) {
        outputService.outputString("""
                        <><><><><><><><><><><>\s
                        Comment id %d, text %s\s
                        <><><><><><><><><><><>\s
                        """, comment.getId(), comment.getText());
    }
}
