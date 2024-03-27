package ru.otus.mar.booklibrary.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.mar.booklibrary.dto.BookCommentDto;
import ru.otus.mar.booklibrary.dto.BookDto;
import ru.otus.mar.booklibrary.model.Book;
import ru.otus.mar.booklibrary.model.BookComment;
import ru.otus.mar.booklibrary.repository.BookCommentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookCommentServiceTest {

    private static final BookCommentDto COMMENT_DTO = new BookCommentDto(null,
            new BookDto("Book_1", null, null), "Comment_1");

    private static final BookComment COMMENT = new BookComment(null,
            new Book("Book_1", null, null), "Comment_1");

    @Autowired
    private BookCommentService service;

    @MockBean
    private BookCommentRepository repo;

    @Test
    void create() {
        when(repo.save(any(BookComment.class))).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO, service.create(COMMENT_DTO));
    }

    @Test
    void update() {
        when(repo.save(any())).thenReturn(COMMENT);
        assertEquals(COMMENT_DTO, service.update(COMMENT_DTO));
    }

    @Test
    void delete() {
        service.delete(COMMENT_DTO.getId());
        verify(repo, times(1)).deleteById(COMMENT.getId());
    }
}
