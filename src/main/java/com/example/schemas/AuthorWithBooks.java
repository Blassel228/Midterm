package com.example.schemas;

import com.example.model.Author;
import com.example.model.Book;
import lombok.Getter;

import java.util.List;

@Getter
public class AuthorWithBooks {

    private Author author;
    private List<Book> books;

    public AuthorWithBooks(Author author, List<Book> books) {
        this.author = author;
        this.books = books;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
