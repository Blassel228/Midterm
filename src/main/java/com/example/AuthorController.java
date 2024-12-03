package com.example;

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

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return authorService.updateAuthor(id, author);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return authorService.getAuthorById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/books")
    public AuthorWithBooks getAuthorWithBooks(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        List<Book> books = bookService.getBooksByAuthorId(id);

        return new AuthorWithBooks(author, books);
    }

}
