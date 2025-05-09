package com.example.controller;

import com.example.service.AuthorService;
import com.example.schemas.AuthorWithBooks;
import com.example.service.BookService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.Author;
import com.example.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @GetMapping()
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return authorService.getAuthorById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @GetMapping("/{id}/books")
    public AuthorWithBooks getAuthorWithBooks(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<Book> books = bookService.getBooksByAuthorId(id);

        return new AuthorWithBooks(author, books);
    }
}

