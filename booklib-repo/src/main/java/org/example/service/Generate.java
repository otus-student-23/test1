package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Comment;
import org.example.model.Image;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class Generate {

    @Autowired
    private final AuthorRepository authorRepo;

    @Autowired
    private final BookRepository bookRepo;

    @PostConstruct
    public void init() {
        Author author = new Author(null, "author_a", new Image(null, new byte[]{1}), new ArrayList<>());
        Book book = new Book(null, "book_b", new Image(null, new byte[]{1}), author, new ArrayList<>(), new ArrayList<>());
        book.getComments().add(new Comment(null, "a", book));
        book.getComments().add(new Comment(null, "b", book));
        book.getComments().add(new Comment(null, "c", book));
        author.getBooks().add(book);
        authorRepo.saveAndFlush(author);

        //authorRepo.delete(author);

        //author.setImage(null);
        //authorRepo.saveAndFlush(author);

        book.getComments().clear();
        //book.setComments(null);
        bookRepo.saveAndFlush(book);
    }
}
