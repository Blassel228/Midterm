package com.example;

import com.example.model.Book;
import com.example.model.Author;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book.getAuthor().getId(), book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/{id}")
    public BookSummary getBook(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/{id}/details")
    public Book getBookDetails(@PathVariable Long id) {
        return bookService.getBookWithAuthor(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }


    @GetMapping("/{bookId}/authors")
    public List<Author> getAuthorsByBookId(@PathVariable Long bookId) {
        return bookService.getAllAuthorsByBookId(bookId);
    }
}
