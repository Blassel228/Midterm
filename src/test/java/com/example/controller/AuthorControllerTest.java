package com.example.controller;

import com.example.service.AuthorService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private ObjectMapper objectMapper;

    private Author sampleAuthor;
    private List<Book> sampleBooks;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();

        sampleAuthor = new Author("John Doe", 1L);

        Book book1 = new Book(1L, "Book 1", sampleAuthor);
        Book book2 = new Book(2L, "Book 2", sampleAuthor);

        sampleBooks = Arrays.asList(book1, book2);

        mockMvc = MockMvcBuilders.standaloneSetup(authorController).build();
    }

    @Test
    void testCreateAuthor() throws Exception {
        when(authorService.saveAuthor(any(Author.class))).thenReturn(sampleAuthor);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$.name").value(sampleAuthor.getName()));

        verify(authorService, times(1)).saveAuthor(any(Author.class));
    }

    @Test
    void testUpdateAuthor() throws Exception {
        when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(sampleAuthor);

        mockMvc.perform(put("/authors/{id}", sampleAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$.name").value(sampleAuthor.getName()));

        verify(authorService, times(1)).updateAuthor(anyLong(), any(Author.class));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        doNothing().when(authorService).deleteAuthor(anyLong());

        mockMvc.perform(delete("/authors/{id}", sampleAuthor.getId()))
                .andExpect(status().isOk());

        verify(authorService, times(1)).deleteAuthor(anyLong());
    }

    @Test
    void testGetAllAuthors() throws Exception {
        when(authorService.getAllAuthors()).thenReturn(List.of(sampleAuthor));

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$[0].name").value(sampleAuthor.getName()));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    void testGetAuthorById() throws Exception {
        when(authorService.getAuthorById(anyLong())).thenReturn(Optional.of(sampleAuthor));

        mockMvc.perform(get("/authors/{id}", sampleAuthor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$.name").value(sampleAuthor.getName()));

        verify(authorService, times(1)).getAuthorById(anyLong());
    }
}
