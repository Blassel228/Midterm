package com.example.service;

import com.example.schemas.BookSummary;
import com.example.model.Author;
import com.example.model.Book;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public BookSummary getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return new BookSummary(book.getId(), book.getTitle());
    }

    public Book saveBook(Long authorId, Book book) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setTitle(updatedBook.getTitle());
                    Author author = authorRepository.findById(updatedBook.getAuthor().getId())
                            .orElseThrow(() -> new RuntimeException("Author not found"));
                    book.setAuthor(author);
                    return bookRepository.save(book);
                })
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Optional<Book> getBookWithAuthor(Long id) {
        return bookRepository.findById(id);
    }

    public List<Author> getAllAuthorsByBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return List.of(book.get().getAuthor());
        } else {
            throw new RuntimeException("Book not found");
        }
    }

    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthor_Id(authorId);
    }
}
