package com.example.controller;

import com.example.service.BookService;
import com.example.schemas.BookSummary;
import com.example.model.Author;
import com.example.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private ObjectMapper objectMapper;

    private Book book;

    private Author author;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        author = new Author("Author Name", 1L);
        book = new Book(1L,"Book Title", author);

        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testCreateBook() throws Exception {
        when(bookService.saveBook(any(Long.class), any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author.id").value(book.getAuthor().getId()))
                .andExpect(jsonPath("$.author.name").value(book.getAuthor().getName()));

        verify(bookService, times(1)).saveBook(any(Long.class), any(Book.class));
    }

    @Test
    void testGetBookById() throws Exception {
        BookSummary bookSummary = new BookSummary(book.getId(), book.getTitle());
        when(bookService.getBookById(book.getId())).thenReturn(bookSummary);

        mockMvc.perform(get("/books/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()));

        verify(bookService, times(1)).getBookById(book.getId());
    }

    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(eq(book.getId()), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/books/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author.id").value(book.getAuthor().getId()))
                .andExpect(jsonPath("$.author.name").value(book.getAuthor().getName()));

        verify(bookService, times(1)).updateBook(eq(book.getId()), any(Book.class));
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(book.getId());

        mockMvc.perform(delete("/books/{id}", book.getId()))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(book.getId());
    }

    @Test
    void testGetBookDetails() throws Exception {
        when(bookService.getBookWithAuthor(book.getId())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/books/{id}/details", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId()))
                .andExpect(jsonPath("$.title").value(book.getTitle()))
                .andExpect(jsonPath("$.author.id").value(book.getAuthor().getId()))
                .andExpect(jsonPath("$.author.name").value(book.getAuthor().getName()));

        verify(bookService, times(1)).getBookWithAuthor(book.getId());
    }
}