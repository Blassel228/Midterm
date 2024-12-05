package com.example.controller;

import com.example.AuthorController;
import com.example.AuthorService;
import com.example.model.Author;
import com.example.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    private Author sampleAuthor;
    private List<Book> sampleBooks;

    @BeforeEach
    void setUp() {
        sampleAuthor = new Author();
        sampleAuthor.setId(1L);
        sampleAuthor.setName("John Doe");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book 1");
        book1.setAuthor(sampleAuthor);

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book 2");
        book2.setAuthor(sampleAuthor);

        sampleBooks = Arrays.asList(book1, book2);
    }

    @Test
    void testCreateAuthor() throws Exception {
        Mockito.when(authorService.saveAuthor(any(Author.class))).thenReturn(sampleAuthor);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$.name").value(sampleAuthor.getName()));
    }

    @Test
    void testUpdateAuthor() throws Exception {
        Mockito.when(authorService.updateAuthor(anyLong(), any(Author.class))).thenReturn(sampleAuthor);

        mockMvc.perform(put("/authors/{id}", sampleAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$.name").value(sampleAuthor.getName()));
    }

    @Test
    void testDeleteAuthor() throws Exception {
        Mockito.doNothing().when(authorService).deleteAuthor(anyLong());

        mockMvc.perform(delete("/authors/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllAuthors() throws Exception {
        Mockito.when(authorService.getAllAuthors()).thenReturn(List.of(sampleAuthor));

        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$[0].name").value(sampleAuthor.getName()));
    }

    @Test
    void testGetAuthorById() throws Exception {
        Mockito.when(authorService.getAuthorById(anyLong())).thenReturn(Optional.of(sampleAuthor));

        mockMvc.perform(get("/authors/{id}", sampleAuthor.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleAuthor.getId()))
                .andExpect(jsonPath("$.name").value(sampleAuthor.getName()));
    }
}
